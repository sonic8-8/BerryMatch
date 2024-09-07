const axios = require("axios");

const userStates = {}; // 방 별로 유저들의 준비 상태를 저장하는 객체

module.exports = (io, socket) => {

    axios.get("http://localhost:8085/api/match/completed")
    .then(res => {
        const { date,  teamAUsers, teamBUsers } = res.data; // 데이터 추출
        
        const formattedData = {
            date,
            members: {
                teamA: teamAUsers.map(member => ({
                    id: member.id,
                    img: member.img,
                    state: member.state
                })),
                teamB: teamBUsers.map(member => ({
                    id: member.id,
                    img: member.img,
                    state: member.state
                }))
            }
        };
        
        const allUsers = [...teamAUsers,teamBUsers];
        
        // 메인 서버로 채팅방 정보 전송 (채팅방 ID를 받아오기)
        axios.post("http://localhost:8085/api/chatRoom", {
            date,
            users: allUsers
        })
        .then(res => {
            const newRoomId = res.data.roomId; // 생성된 채팅방 ID를 받아옴
            console.log("채팅방 정보 저장 성공, 생성된 채팅방 ID:", newRoomId);

            // 클라이언트에게 채팅방 ID 전달
            io.to(socket.id).emit("create new room", { roomId: newRoomId, data: formattedData });
        })
        .catch(error => {
            console.error("채팅방 정보 저장 실패:", error);
            io.to(socket.id).emit("error", "채팅방 생성 실패");
        });
    })
    .catch(error => {
        console.error("매칭 정보를 불러오는 중 오류 발생:", error);
    });
    
    // 채팅방 입장: 사용자가 해당 방에 접근 권한이 있는지 확인 및 
    socket.on("join Room", async ({ roomId, user }) => {
        
        if (user && user.rooms && user.rooms.includes(roomId)) {
            socket.join(roomId);
            socket.room = roomId;             
            socket.emit("room Joined", { roomId: roomId });
            if (!userStates[room]) {
                userStates[room] = {}; // 방 상태 초기화
            }
            try {
                let responseTimer;
                let responseHistory;
                // 매칭 성사 시간 불러오기
                const matchResponse = await axios.get("http://localhost:8085/api/chat/match/completed");
                responseTimer = matchResponse.data.date;
                console.log("매칭 성사 시간 불러오기 성공");

                // 채팅 내역 불러오기
                const chatResponse = await axios.get(`http://localhost:8085/api/chatRoom/${roomId}/msgs`);
                responseHistory = chatResponse.data;
                console.log("채팅 내역 불러오기 성공");
                
                const startTime = new Date(responseTimer); // 매칭 성사 시간
                const endTime = new Date(startTime.getTime() + 30 * 60 * 1000); 

                const chatHistory = responseHistory.data;
                socket.emit("load all msgs", chatHistory);

                // 현재 시간 기준으로 남은 시간 계산하여 타이머 설정
                const timeRemaining = endTime - new Date();

                if (timeRemaining > 0) {
                    // 남은 시간 내에 준비 상태를 확인하기 위한 타이머 설정
                    setTimeout(() => {
                        const allReady = Object.values(userStates[roomId] || {}).every(user => user.ready);

                        if (!allReady) {
                            io.in(roomId).emit("room disbanded"); // 방 폭파 이벤트 전송
                            delete userStates[roomId]; // 방 상태 삭제
                            io.socketsLeave(roomId); // 방에 있는 모든 사용자 퇴장
                        }
                    }, timeRemaining);
                } else {
                    // 시간이 이미 지난 경우 바로 방 폭파
                    io.in(roomId).emit("room disbanded");
                    delete userStates[roomId];
                    io.socketsLeave(roomId);
                }
            } catch (error) {
                console.error("Error fetching start time:", error);
            }
        } else {
            socket.emit("join error"); // 접근 권한이 없는 경우 에러 전송
        }
    });
    
    socket.on("send msg", (res) => {
        // 받은 메시지를 클라이언트에게 전송
        io.to(res.roomId).emit("receive msg", {
            user: res.user,
            msg: res.msg,
            time: res.time
        });

        // 받은 메시지를 DB에 저장하기 위해 SpringBoot 서버로 전송
        axios.post("http://localhost:8085/api/chat/save-msg",{
            userId: res.user,
            chatRoom:res.roomId,
            message: res.msg,
            createdAt: res.time
        })
        .then(res =>{
            console.log("DB 저장 위한 메시지 메인 서버로 전송 성공");
        })
        .catch(error => {
            console.log("DB 저장 위한 메시지 메인 서버로 전송 실패", error);
        })
    });

    // 경기 시간 알려주기(종료 버튼 활성화)
    io.to(roomId).emit("game time");

};
