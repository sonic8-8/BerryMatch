const axios = require('axios');
const { handleSocketConnection } = require('./utility');  // 유틸리티 함수 임포트

function game(token, matchId, socket, io) {

    const id = handleSocketConnection(token, matchId, socket);  // 유틸리티 함수 호출
    socket.join(matchId);  // 같은 matchId로 룸에 참가

    socket.on('endGame', ({ id, nickname, matchId }) => {

        io.to(matchId).emit('endGameVoteStatusChanged', { id, nickname, isVote: true});

        axios.post('http://localhost:8085/api/gameEndVote', 
            {
                'userId': id,
                'nickname': nickname,
                'gameEndVoteStatus': 'YES'
            }
        )

    });

        // game 생성

        // game으로 데이터 넘기기 ()

        // 매칭 방 삭제 요청

        // 경기 종료 시 MatchStatus FINISHED로 변경

        // FINSIED 변경 시 어떤 로직 처리됨?


}

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
