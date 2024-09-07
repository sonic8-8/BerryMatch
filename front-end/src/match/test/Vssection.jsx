import React, { useState, useEffect, useRef } from 'react';
import io from 'socket.io-client';
import './VSSection.css';
import Cookies from 'js-cookie';
import { jwtDecode } from 'jwt-decode'; 
import axios from 'axios';

const VSSection = () => {
    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState('');
    const [headerClicked, setHeaderClicked] = useState(false);
    const [socket, setSocket] = useState(null);
    const accessToken = Cookies.get('accessToken');
    const [MatchId, setMatchId] = useState(null);
    const [nickname, setNickname] = useState('');
    
    const chatboxRef = useRef(null); // chatbox에 대한 참조 생성

    let id = null;
    if (accessToken) {
        const decodedToken = jwtDecode(accessToken);
        id = Number(decodedToken.id);
    }

    // 매치 ID 요청
    useEffect(() => {
        const requestData = { id: id };
    
        axios.post('http://localhost:8085/api/chat/room', requestData, {
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Content-Type': 'application/json',
                'Cookie': `accessToken=${accessToken};`
            },
            withCredentials: true,
        })
        .then(response => {
            console.log("서버 응답 데이터:", response.data);
            const MatchId = response.data.data?.matchId;
            if (MatchId) {
                setMatchId(MatchId);
                setNickname(response.data.data.nickname);
                console.log("MatchId가 설정되었습니다:", MatchId); 
            } else {
                console.error("매치 ID가 없습니다.");
            }
        })
        .catch(error => {
            console.error('Error sending match request:', error);
        });
    }, [id, accessToken]);

    // 과거 메시지 불러오기
    useEffect(() => {
        console.log("메시지내역", MatchId);
        
        if (MatchId) {
            axios.get(`http://localhost:8085/api/chat/${MatchId}`, {
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                },
                withCredentials: true,
            })
            .then(response => {
                console.log(response.data);
                const pastMessages = response.data.data || [];

                const formattedMessages = pastMessages.map(msg => {
                    console.log("현재 유저 ID: ", id, "메시지 작성자 ID: ", msg.id);
                    return {
                        sender: msg.id === id ? 'me' : 'other',  // 내 메시지와 상대방 메시지 구분
                        text: msg.message[0],
                        nickname: msg.nickname
                    };
                });

                setMessages(formattedMessages);
            })
            .catch(error => {
                console.error('Error fetching past messages:', error);
            });
        }
    }, [MatchId, accessToken]);


    // 메시지가 업데이트될 때마다 자동 스크롤
    useEffect(() => {
        if (chatboxRef.current) {
            chatboxRef.current.scrollTop = chatboxRef.current.scrollHeight;  // 스크롤을 최하단으로 이동
        }
    }, [messages]);

    useEffect(() => {   
        // matchId가 설정되었을 때만 소켓 연결
        if (MatchId) {
            const newSocket = io('http://localhost:9000', {
                query: { MatchId: MatchId },
                auth: {
                    token: accessToken
                }
            });
            setSocket(newSocket);

            newSocket.on('connect', () => {
                console.log('Connected to Node.js server with matchId:', MatchId);
            });

            newSocket.on('message', (data) => {
                console.log('Received message:', data);
                setMessages(prevMessages => [...prevMessages, data]);
            });

            return () => {
                newSocket.disconnect();
            };
        }
    }, [MatchId, accessToken]);

    const handleSendMessage = () => {
        if (inputValue.trim() !== '') {
            const messageData = {
                text: inputValue,
                nickname: nickname,
                id: id
            };

            // 메시지 리스트에 추가
            const newMessages = [...messages, { sender: 'me', text: inputValue, nickname: nickname }];
            setMessages(newMessages);
            setInputValue('');

            // 서버로 메시지 전송
            if (socket) {
                socket.emit('sendMessage', messageData);  // 메시지와 닉네임을 함께 전송
            }
        }
    };

    const handleInputChange = (e) => {
        setInputValue(e.target.value);
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSendMessage();
        }
    };

    const handleHeaderClick = () => {
        setHeaderClicked(prevState => !prevState);
    };

    return (
    <div className="vs">
        <button
            className={`vs-header ${headerClicked ? 'clicked' : ''}`}
            onClick={handleHeaderClick}
            aria-label="Ready Button"
        >
            Ready
        </button>

        <div className="chatbox" ref={chatboxRef}>
            {messages.map((msg, index) => (
                <div
                    key={index}
                    className={`chat-message-container ${msg.sender === 'me' ? 'me-container' : 'other-container'}`}
                >
                    {/* 메시지 작성자의 닉네임 */}
                    <div className={`chat-nickname ${msg.sender}`}>
                        {msg.nickname}
                    </div>
                    {/* 메시지 내용 */}
                    <div className={`chat-message ${msg.sender}`}>
                        {msg.text}
                    </div>
                </div>
            ))}
        </div>

        <div className="input-container">
            <input
                type="text"
                placeholder="메시지를 입력하세요..."
                value={inputValue}
                onChange={handleInputChange}
                onKeyPress={handleKeyPress}
                className="chatbox-input"
            />
            <button className="send-button" onClick={handleSendMessage}>
                전송
            </button>
        </div>
    </div>
);

};

export default VSSection;
