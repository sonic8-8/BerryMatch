const express = require('express');
const http = require('http');
const { Server } = require('socket.io');
const { newchatToken } = require('./newchat');  // tokenHandler 가져오기
const app = express();
const server = http.createServer(app);

const io = new Server(server, {
  cors: {
    origin: 'http://localhost:3000',
    methods: ['GET', 'POST'],
    credentials: true
  }
});

io.on('connection', (socket) => {
    console.log('User connected');
  
    // 클라이언트로부터 토큰 및 MatchId 수신
    const token = socket.handshake.auth.token;
    const matchIdString = socket.handshake.query.MatchId;
    
  
    try {
      const matchId = BigInt(matchIdString);  
      // newchatToken 함수 호출
      newchatToken(token, matchId, socket, io);
      
    } catch (error) {
      socket.disconnect();
    }
  
    socket.on('disconnect', () => {
      console.log('User disconnected');
    });

});

server.listen(9000, () => {
  console.log('Server listening on port 9000');
});
