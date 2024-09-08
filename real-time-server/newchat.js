const axios = require('axios');
const jwt = require('jsonwebtoken');

// tokenHandler.js
function newchatToken(token, matchId, socket, io) {
    console.log('Processing new chat token:', token);
    console.log('Processing MatchId:', matchId);

    let id;

    if (token) {
        const decodedToken = jwt.decode(token);  // JWT 토큰 디코딩
        id = Number(decodedToken.id);  // 사용자 식별자 추출
    }
    console.log('Decoded User ID:', id);

    // 사용자가 매칭 ID에 해당하는 방에 조인
    socket.join(matchId);
    console.log(`User with ID ${id} joined room: ${matchId}`);

    // 메시지를 수신하고 다시 해당 매칭 ID의 방으로 브로드캐스트
    socket.on('sendMessage', (messageData) => {
        const requestData = {
            id: id,  // 디코딩된 사용자 식별자
            message: messageData.text,  // 수신된 메시지
            matchId: Number(matchId),
        };

        console.log('Sending requestData to Spring Boot:', requestData);

        // 해당 매칭 ID의 방에서 자신을 제외한 다른 사용자들에게만 메시지 브로드캐스트
        socket.broadcast.to(matchId).emit('message', {
            sender: 'other',
            text: messageData.text,
            nickname: messageData.nickname,  // 닉네임을 함께 브로드캐스트
        });

        console.log('Broadcasting message to other clients in room:', matchId);

        // Spring Boot로 비동기 메시지 저장 요청
        axios.post('http://localhost:8085/api/chat/save-msg', requestData, {
            headers: {
                'Authorization': `Bearer ${token}`,  // JWT 토큰을 Authorization 헤더에 포함
                'Content-Type': 'application/json',
            },
            withCredentials: true,  // 쿠키 및 인증 정보 포함 (CORS 설정 필요)
        })
        .then(response => {
            console.log('Message saved successfully:', response.data);
        })
        .catch(error => {
            console.error('Error saving message to Spring Boot:', error);
        });
    });

    // 사용자가 연결을 끊었을 때 로그 출력
    socket.on('disconnect', () => {
        console.log(`User with ID ${id} disconnected from room: ${matchId}`);
    });
}

module.exports = { newchatToken };
