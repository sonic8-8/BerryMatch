const axios = require('axios');
const { handleSocketConnection } = require('./utility');


const usersReadyStatus = {};

function matchready(token, matchId, socket, io) {

    const id = handleSocketConnection(token, matchId, socket);
    socket.join(matchId);  // 같은 matchId로 룸에 참가



    // 준비 상태 처리
    socket.on('ready', ({ id, nickname }) => {
        // 유저를 준비 상태로 설정
        usersReadyStatus[id] = true;

        // 모든 사용자에게 개별 준비 상태 전송
        io.to(matchId).emit('userReadyStatusChanged', { id, nickname, isReady: true });

        const requestData = { id: id };

        axios.post('http://localhost:8085/api/ready', requestData, {
            headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
            withCredentials: true,
        })
        .then(response => {
            console.log('Message saved successfully:', JSON.stringify(response.data, null, 2));

            // 모든 유저가 준비 상태인지 확인
            const allUsersReady = Object.values(usersReadyStatus).every(status => status === true);

            if (allUsersReady) {
                // 모든 유저가 준비된 상태인 경우 신호 전송
                io.to(matchId).emit('userReadyStatusChanged', { allUsersReady: true });
                console.log('All users are ready. Starting the game.');
            }
        })
        .catch(error => {
            console.error('Error saving message to Spring Boot:', error);
        });
    });



    // 준비 해제 처리 (본인 포함 모든 사용자에게 전송)
    socket.on('notReady', ({ id, nickname }) => {
        console.log(`User ${nickname} is not ready in match ${matchId}`);
        // 모든 사용자에게 준비 해제 상태 전송
        io.to(matchId).emit('userReadyStatusChanged', { id, nickname, isReady: false });
    
        const requestData = {
            id: id,
        };

        axios.post('http://localhost:8085/api/waiting', requestData, {
            headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
            withCredentials: true,
        })
        .then(response => {
            console.log('Message saved successfully:', response.data);
        })
        .catch(error => {
            console.error('Error saving message to Spring Boot:', error);
        });
    });



    // 사용자 나가기 처리
    socket.on('leaveuser', ({ id, nickname }) => {
        console.log(`User with ID ${id} and nickname ${nickname} is leaving match ${matchId}`);

        // 모든 클라이언트에게 나간 유저의 ID와 닉네임 전송
        io.to(matchId).emit('userLeft', { id, nickname });

        // 해당 유저를 matchId 방에서 제거
        socket.leave(matchId);

        const requestData = {
            id: id,
        };
        //경로 일부러 빼놓음
        axios.post('http://localhost:8085/api/?????', requestData, {
            headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
            withCredentials: true,
        })
        .then(response => {
            console.log('Message saved successfully:', response.data);
        })
        .catch(error => {
            console.error('Error saving message to Spring Boot:', error);
        });     
    });




    // 연결 해제 처리
    socket.on('disconnect', () => {
        console.log(`User disconnected from room: ${matchId}`);
    });
}

module.exports = { matchready };
