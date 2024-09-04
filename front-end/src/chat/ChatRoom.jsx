import React, { useEffect, useState, useRef } from "react";
import { io } from "socket.io-client";
import ChatMessage from "./ChatMessage";
import ChatMembersList from "./ChatMembersList";
import Styles from "./ChatRoom.module.css";

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

        // 매칭된 경기 일자 불러오기
        socket.current.on("matching info to client",(data)=>{
          setGameDate(data.date);
        });

        // 사용자가 방에 입장하는 이벤트
        socket.current.emit("join Room", { room: roomName, token: token });

        // 유저 정보 불러오기
        socket.current.on("load user info", (data) => {
            setUserId(data);
        });

        // 룸 정보 불러오기
        socket.current.on("roomJoined", (data) => {
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
            e.target.value = newReady ? "준비 완료" : "준비 취소";
            socket.current.emit(newReady ? "ready on" : "ready off", { room: currentRoom });
            return newReady;
        });
    };

    // 경기 종료
    const endGame = () => {
        socket.current.emit("end game");
    };

    // 전송 버튼 클릭 시 메세지 전송
    const sendMsgHandler = () => {
        if (userInputMsg.trim()) {
            socket.current.emit("send msg", {
                author: userId,
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
        <ChatMembersList team="A"/>
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
                        author={msgContent.author}
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
        <ChatMembersList team="B"/>
      </div>
    );
};

export default ChatRoom;
