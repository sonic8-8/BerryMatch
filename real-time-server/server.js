const express = require('express');
const app = express();
const http = require('http').createServer(app);
const io = require('socket.io')(http);

app.use(express.json()); // JSON 형식의 데이터를 파싱하기 위해 필요

// POST 요청을 처리하는 엔드포인트
app.post('/api/match/completed', (req, res) => {
    const { teamAUsers, teamBUsers, requestingUserId } = req.body;

    console.log('Received match data:', { teamAUsers, teamBUsers, requestingUserId });

    // Socket.io를 통해 클라이언트에게 데이터 전송
    io.emit('matchUpdate', { teamAUsers, teamBUsers });

    res.status(200).send('Match data received and broadcasted.');
});

io.on('connection', (socket) => {
    console.log('A user connected');

    socket.on('disconnect', () => {
        console.log('A user disconnected');
    });
});

http.listen(9000, () => {
    console.log('Server running on port 9000');
});
