const axios = require('axios');
const { handleSocketConnection } = require('./utility');  // 유틸리티 함수 임포트

function matchuser(token, matchId, socket, io) {


    const id = handleSocketConnection(token, matchId, socket);  // 유틸리티 함수 호출


    const fetchData = async () => {
        const params = { id: id };

        axios.get('http://localhost:8085/api/matchusers', {
            params: params,
            headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
            withCredentials: true,
        })
        .then(response => {
            const users = response.data.data || [];
            
            
            io.to(matchId).emit('matchUserData', users);
            
            
        })
        .catch(error => {
            console.error('Error fetching match users:', error);
        });
    };

    fetchData();
}

module.exports = { matchuser };
