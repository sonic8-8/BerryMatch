const axios = require("axios");

const userStates = {}; // 방 별로 유저들의 준비 상태를 저장하는 객체

module.exports = (io, socket) => {

    // 매칭 완료된 경기 정보를 가져와 새 채팅방 생성
    axios.get("http://localhost:8085/api/match/completed")
    .then(res => {
        const { date, teamAUsers, teamBUsers } = res.data; // 데이터 추출
        
        const formattedData = {
            date,
            members: {
                teamA: teamAUsers.map(member => ({
                    id: member.id,
                    img: member.img,
                    state: member.state,
                })),
                teamB: teamBUsers.map(member => ({
                    id: member.id,
                    img: member.img,
                    state: member.state,
                })),
            },
        };

        const allUsers = [...teamAUsers, ...teamBUsers];

        // 메인 서버로 채팅방 정보 전송 후 채팅방 ID 받아옴
        axios.post("http://localhost:8085/api/chatRoom", {
            date,
            users: allUsers,
        })
        .then((res) => {
            const newRoomId = res.data.roomId; // 생성된 채팅방 ID
            console.log("채팅방 정보 저장 성공, 생성된 채팅방 ID:", newRoomId);

            // 클라이언트에게 채팅방 ID 및 경기 정보 전달
            io.to(socket.id).emit("create new room", { roomId: newRoomId, data: formattedData });
        })
        .catch((error) => {
            console.error("채팅방 정보 저장 실패:", error);
            io.to(socket.id).emit("error", "채팅방 생성 실패");
        });
    })
    .catch((error) => {
        console.error("매칭 정보를 불러오는 중 오류 발생:", error);
    });
    
   // 채팅방에 입장하는 이벤트 처리
   socket.on("join Room", async ({ roomId, userId }) => {
    try {
        // 방에 입장할 권한이 있는지 확인
        const response = await axios.get(`http://localhost:8085/api/user/${userId}/rooms`);
        const userRooms = response.data.rooms;

        if (userRooms.includes(roomId)) {
            socket.join(roomId);
            socket.room = roomId;
            socket.emit("room Joined", { roomId });

            // 유저 상태 저장 공간 초기화
            if (!userStates[roomId]) {
                userStates[roomId] = {};
            }

            // 기존 채팅 기록 로드
            const chatResponse = await axios.get(`http://localhost:8085/api/chatRoom/${roomId}/msgs`);
            const chatHistory = chatResponse.data;
            socket.emit("load all msgs", chatHistory);

            // 경기 시간 및 남은 시간 계산
            const matchResponse = await axios.get("http://localhost:8085/api/chat/match/completed");
            const startTime = new Date(matchResponse.data.date);
            const endTime = new Date(startTime.getTime() + 30 * 60 * 1000); // 경기 시간 30분 후

            // 타이머로 경기 시작 시간까지 남은 시간 체크
            const timeRemaining = endTime - new Date();
            if (timeRemaining > 0) {
                setTimeout(() => {
                    const allReady = Object.values(userStates[roomId] || {}).every((user) => user.ready);

                    if (!allReady) {
                        io.in(roomId).emit("room disbanded");
                        delete userStates[roomId];
                        io.socketsLeave(roomId);
                    }
                }, timeRemaining);
            } else {
                io.in(roomId).emit("room disbanded");
                delete userStates[roomId];
                io.socketsLeave(roomId);
            }
        } else {
            socket.emit("join error", "채팅방에 접근 권한이 없습니다.");
        }
    } catch (error) {
        console.error("방 입장 처리 중 오류 발생:", error);
        socket.emit("join error", "방 입장 중 오류가 발생했습니다.");
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
    socket.on("game time", (roomId) => {
        io.to(roomId).emit("game time");
    });
};
