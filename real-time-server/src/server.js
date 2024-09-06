const express = require("express");
const cors = require("cors"); 
const app = express();
const { createProxyMiddleware } = require("http-proxy-middleware");
const http = require("http");
const { Server } = require("socket.io");

const loadSocketEvents = require("./socket");

const server = http.createServer(app);

// Express의 CORS 설정
app.use(cors({
  origin: "*",  // 모든 출처 허용. 필요에 따라 특정 출처로 제한 가능
}));

const io = new Server(server, {
  cors: {
    origin: "*",  // Socket.IO에 대한 CORS 설정
  }
});

// 소켓 이벤트 핸들러 로드
loadSocketEvents(io);

app.use("/api", createProxyMiddleware({
  target: "http://localhost:8085", // 메인 서버 주소
  changeOrigin: true,
}));

app.get("/", (req, res) => {
  res.send("9000번 포트는 실시간 통신 서버입니다.");
});

server.listen(9000, () => {
  console.log("http://localhost:9000에서 실시간 통신 서버 실행중");
});
