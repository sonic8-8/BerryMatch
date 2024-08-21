const express = require('express')
const app = express()
const { createProxyMiddleware } = require('http-proxy-middleware');
const axios = require('axios');

app.listen(9000, () => {
    console.log('http://localhost:9000에서 실시간 통신 서버 실행중')
})

// 프록시 설정 : /api 로 들어오는 요청을 메인 서버로 보냄
app.use('/api', createProxyMiddleware({
  target: 'http://localhost:8085', // 메인 서버 주소
  changeOrigin: true,
}));

app.get('/', (요청, 응답) => {
  응답.send('9000번 포트는 실시간 통신 서버입니다.')
});
