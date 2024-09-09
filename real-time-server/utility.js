const jwt = require('jsonwebtoken');

// 유틸리티 함수: 토큰 디코딩과 소켓 룸 참여 처리
function handleSocketConnection(token, matchId, socket) {
    let id;

    // 토큰을 디코딩하여 사용자 식별자를 추출
    if (token) {
        const decodedToken = jwt.decode(token);  // JWT 토큰 디코딩
        id = Number(decodedToken.id);  // 사용자 식별자 추출
    }
    

    // 클라이언트를 matchId 룸에 참가시킴
    socket.join(matchId);

    return id;
}

module.exports = { handleSocketConnection };
