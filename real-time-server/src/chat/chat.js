const axios = require("axios");

const userStates = {}; // 방 별로 유저들의 준비 상태를 저장하는 객체

module.exports = (io, socket) => {

    // 채팅방 정보(타이틀, 채팅방 멤버 정보) 가져오기
    socket.on("send matching info", (data) => {
        axios.get("http://localhost:8085/api/match/completed")
        .then(res=>{
            io.to(room).emit("matching info to client", {
                date:data.date,
                members: data.members.map(member => ({
                    id: member.id,
                    img:member.img,
                    record: member.record,
                    state: member.state
                }))
            });
        })
        .catch(error =>{
            console.log("매칭 정보 못불러옴");            
        })
    });
    
    // 채팅방 입장: 사용자가 해당 방에 접근 권한이 있는지 확인
    socket.on("join Room", async ({ room }) => {
        
        if (user && user.rooms && user.rooms.includes(room)) {
            socket.join(room);
            socket.room = roomName;             
            socket.emit("room Joined", { room: roomName });
            if (!userStates[room]) {
                userStates[room] = {}; // 방 상태 초기화
            }
            try {
                // Spring Boot 서버에서 매칭 성사 시간 & 채팅 내역 불러오기
                const responseTimer = await axios.get("http://localhost:8085/api/chat/match/completed");
                const responseHistory = await axios.get(`http://localhost:8085/api/chatRoom/${roomId}/info`);
                
                const startTime = new Date(responseTimer.data.startTime); // 매칭 성사 시간
                const endTime = new Date(startTime.getTime() + 30 * 60 * 1000); 

                const chatHistory = responseHistory.data;
                socket.emit("load all msgs", chatHistory);

                // 현재 시간 기준으로 남은 시간 계산하여 타이머 설정
                const timeRemaining = endTime - new Date();

                if (timeRemaining > 0) {
                    // 남은 시간 내에 준비 상태를 확인하기 위한 타이머 설정
                    setTimeout(() => {
                        const allReady = Object.values(userStates[room] || {}).every(user => user.ready);

                        if (!allReady) {
                            io.in(room).emit("room disbanded"); // 방 폭파 이벤트 전송
                            delete userStates[room]; // 방 상태 삭제
                            io.socketsLeave(room); // 방에 있는 모든 사용자 퇴장
                        }
                    }, timeRemaining);


                } else {
                    // 시간이 이미 지난 경우 바로 방 폭파
                    io.in(room).emit("room disbanded");
                    delete userStates[room];
                    io.socketsLeave(room);
                }

            } catch (error) {
                console.error("Error fetching start time:", error);
            }

        } else {
            socket.emit("join error"); // 접근 권한이 없는 경우 에러 전송
        }
    });
    
    socket.on("send msg", (data) => {
        // 받은 메시지를 클라이언트에게 전송
        io.to(data.room).emit("receive msg", {
            author: data.user,
            msg: data.msg,
            time: data.time
        });

        // 받은 메시지를 DB에 저장하기 위해 SpringBoot 서버로 전송
        axios.post("http://localhost:8085/api/chat/save-msg",{
            user: data.sender,
            chatRoom:data.room,
            message: data.msg,
            createdAt: data.time
        })
        .then(res =>{
            console.log("DB 저장 위한 메시지 메인 서버로 전송 성공");
        })
        .catch(error => {
            console.log("DB 저장 위한 메시지 메인 서버로 전송 실패");
        })
    });

    // 경기 시간 알려주기(종료 버튼 활성화)
    io.to(room).emit("game time");

};
