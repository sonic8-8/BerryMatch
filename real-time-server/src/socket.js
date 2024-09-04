const chatHandler = require("./chat/chat");
const matchingHandler = require("./matching/matching");

module.exports = (io) => {
    io.on("connection",(socket) => {
        
        // 기능별 이벤트 헨들러 호출
        chatHandler(io, socket);
        matchingHandler(io, socket);

        socket.on("disconnect",()=>{
            // 사용자가 나간 경우
        });
    });

};