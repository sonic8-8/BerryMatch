import React, { useEffect, useState, useRef } from "react";
import io from "socket.io-client";
import axios from "axios";
import ChatMessage from "./ChatMessage";
import ChatMembersList from "./ChatMembersList";
import Styles from "./ChatRoom.module.css";


// 해당 채팅방 정보를 가지고 오면 그걸 가지고 접속해야할거같은데
const ChatRoom = ({ token, roomName }) => {
    const socket = useRef(null);

    const [userId, setUserId] = useState("");
    const [gameDate, setGameDate] = useState("");
    const [currentRoom, setCurrentRoom] = useState(roomName);
    const [userInputMsg, setUserInputMsg] = useState("");
    const [gameReady, setGameReady] = useState(false);
    const [chatMessages, setChatMessages] = useState([]);
    const [readyDisabled, setReadyDisabled] = useState(false);
    const [endDisabled, setEndDisabled] = useState(true);

    useEffect(() => {
        // 서버와의 소켓 연결
        socket.current = io.connect("http://localhost:8085/api/chatRoom", {
            cors: { origin: "*" },
        });

        // 매칭된 경기 일자 불러오기 (matching에서 매칭된 정보 보내줘야함)
        socket.current.on("matching info to client",(data)=>{
            setGameDate(data.date);
        });

        // 사용자가 방에 입장하는 이벤트
        socket.current.emit("join Room", {
             room: roomName,
             token: token
        });

        // 유저 정보 불러오기
        socket.current.on("load user info", (data) => {
            setUserId(data);
        });

        // 룸 정보 불러오기
        socket.current.on("room Joined", (data) => {
            setCurrentRoom(data.room);
        });
    
        // 과거 채팅 내역 불러오기
        socket.current.on("load all msgs", (data) => {
            setChatMessages(data);
        });

        // 실시간 채팅 내역 띄우기
        socket.current.on("receive msg", (msg) => {
            setChatMessages((prevChatMessages) => [...prevChatMessages, msg]);
        });

        // 모든 유저가 준비 완료 시 준비버튼 비활성화
        socket.current.on("all users ready", () => {
            setReadyDisabled(true);
        });

        // 경기 시간이 되면 종료 버튼 활성화
        socket.current.on("game time", () => {
            setEndDisabled(false);
        });

        // 방 폭파 이벤트 처리
        socket.current.on("room disbanded", () => {
            alert("모든 사용자가 준비되지 않아 방이 폭파되었습니다.");
            setReadyDisabled(true);
            setEndDisabled(true);
        });

        // 컴포넌트 언마운트 시 소켓 연결 해제
        return () => {
            socket.current.disconnect();
        };
    }, [roomName, token]);

    // 경기 준비 버튼 이벤트
    const readyGame = (e) => {
        setGameReady((prevReady) => {
            const newReady = !prevReady;
            if(newReady){
                axios.post("http://localhost:8085/api/chatRoom/ready", {
                    user: userId,
                    isReady: false
                })
                .then(res => {
                    console.log("유저 준비 상태 전달 성공:", res.data);
                })
                .catch(error => {
                    console.error("유저 준비 상태 전달 실패:", error);
                });
                e.target.value = "준비 완료"
            }else{
                axios.post("http://localhost:8085/api/chatRoom/ready", {
                    user: userId,
                    isReady: true 
                })
                .then(res => {
                    console.log("유저 준비 상태 전달 성공:", res.data);
                })
                .catch(error => {
                    console.error("유저 준비 상태 전달 실패:", error);
                });
                e.target.value = "준비 취소"
            }
            return newReady;
        });
    };

    // 경기 종료 처리
    const endGame = (e) => {
        // axios로 바로 메인 서버로 
        axios.post("http://localhost:8085/api/chat/end",{
            user:userId,
            chatRoom:currentRoom
        })
        .then(res =>{
            console.log("메인 서버로 경기 종료 확인 및 데이터 처리 요청 완료");            
        })
        .catch(error => {
            console.log("메인 서버로 경기 종료 확인 및 데이터 처리 요청 실패");        
        });
    };

    // 전송 버튼 클릭 시 메세지 전송
    const sendMsgHandler = () => {
        if (userInputMsg.trim()) {
            socket.current.emit("send msg", {
                sender: userId,
                msg: userInputMsg,
                time: new Date().toLocaleTimeString(),
                room: currentRoom,
            });
            setUserInputMsg("");
        }
    };

    // 사용자 입력 처리
    const changeMsg = (e) => {
        setUserInputMsg(e.target.value);
    };

    return (
      <div className={Styles.chat_page}>
        <ChatMembersList team="A" roomId={currentRoom}/>
        <div className={Styles.chat_container}>
            <div className={Styles.chat_header}>
                <div className={Styles.chat_info}>
                  {gameDate}
                </div>
                <div className={Styles.chat_options}>
                    <button
                        className={Styles.game_ready}
                        onClick={readyGame}
                        disabled={readyDisabled}
                    >
                        준비
                    </button>
                    <button
                        className={Styles.game_end}
                        onClick={endGame}
                        disabled={endDisabled}
                    >
                        경기 종료
                    </button>
                </div>
            </div>
            <div className={Styles.chat_content}>
                {chatMessages.map((msgContent, index) => (
                    <ChatMessage
                        key={index}
                        time={msgContent.time}
                        sender={msgContent.sender}
                        msg={msgContent.msg}
                    />
                  
                ))}
            </div>
            <div className={Styles.chat_input}>
                <input
                    type="text"
                    placeholder="메세지를 입력하세요"
                    value={userInputMsg}
                    onChange={changeMsg}
                    onKeyDown={(e) => {
                        if (e.key === "Enter") sendMsgHandler();
                    }}
                />
                <button onClick={sendMsgHandler}>전송</button>
            </div>
        </div>
        <ChatMembersList team="B" roomId={currentRoom}/>
      </div>
    );
};

export default ChatRoom;
