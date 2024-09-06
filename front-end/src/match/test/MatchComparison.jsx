import React, { useEffect, useState } from 'react';
import Team from './Team';
import VSSection from './Vssection';
import './MatchPage.css';
import Home from './Home.png';
import Away from './Away.png';
import Cookies from 'js-cookie';
import {jwtDecode} from 'jwt-decode'; 
import axios from 'axios';

const MatchComparison = () => {
    const [teamAUsers, setTeamAUsers] = useState([]);
    const [teamBUsers, setTeamBUsers] = useState([]);
    const accessToken = Cookies.get('accessToken');
  
    let id = null;
    if (accessToken) {
        const decodedToken = jwtDecode(accessToken);
        id = Number(decodedToken.id);
    }

    const fetchData = async () => {
        const params = { id: id };

        axios.get('http://localhost:8085/api/matchusers', {
          params: params,
          headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json',
          },
          withCredentials: true,
        })
        .then(response => {
          const users = response.data.data || [];
          const teamA = users.filter(user => user.team === 'A_Team');
          const teamB = users.filter(user => user.team === 'B_Team');

          setTeamAUsers(teamA);
          setTeamBUsers(teamB);
        })
        .catch(error => {
          console.error('Error sending match request:', error);
        });
    };

    useEffect(() => {
        fetchData();
    }, []);

    // 매칭 나가기 핸들러
    const handleLeaveMatch = () => {
        

        const requestData= {id:id};
        console.log(requestData);
        
        axios.post('http://localhost:8085/api/matchleave',requestData, {
          headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json',
          },
          withCredentials: true,
        })
        .then(response => {
         
        })
        .catch(error => {
          console.error('Error sending match request:', error);
        });
        
    };
        




    // 준비 버튼 핸들러
    const handleReady = () => {
       
        console.log('준비 버튼 클릭됨');

        const requestData= {id:id};
        console.log(requestData);
        
        axios.post('http://localhost:8085/api/ready',requestData, {
          headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json',
          },
          withCredentials: true,
        })
        .then(response => {
         
        })
        .catch(error => {
          console.error('Error sending match request:', error);
        });
        
    };


 // 준비 버튼 핸들러
 const handleWaiting = () => {
    // 준비 상태 변경 로직
    console.log('준비 해제 버튼 클릭됨');

    const requestData= {id:id};
    console.log(requestData);
    
    axios.post('http://localhost:8085/api/waiting',requestData, {
      headers: {
        'Authorization': `Bearer ${accessToken}`,
        'Content-Type': 'application/json',
      },
      withCredentials: true,
    })
    .then(response => {
     
    })
    .catch(error => {
      console.error('Error sending match request:', error);
    });
    // 준비 상태로 전환하는 로직을 추가합니다.
};










    return (
        <div className="match-container">
            {/* A팀 */}
            <Team 
                teamLabel="A팀" 
                users={teamAUsers} 
                teamLogo={Home} 
                showButton={false} 
            />

            <VSSection />

            {/* B팀 - 여기에만 버튼 추가 */}
            <Team 
                teamLabel="B팀" 
                users={teamBUsers} 
                teamLogo={Away} 
                showButton={true} 
                onLeaveMatch={handleLeaveMatch}  // 매칭 나가기 버튼 핸들러 전달
                onReady={handleReady}  
                onWaiting={handleWaiting}            // 준비 버튼 핸들러
            />
        </div>
    );
};

export default MatchComparison;
