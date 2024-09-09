import React, { useState } from 'react';
import styles from './MatchSetupSubPage.module.css';
import MatchDashboard from '../components/MatchDashboard';
import axios from 'axios';
import Cookies from 'js-cookie';
import { jwtDecode } from 'jwt-decode'; // jwtDecode를 가져올 때는 {} 없이 가져옵니다.
function MatchSetupSubPage() {
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedSport, setSelectedSport] = useState('');
  const [startAvailableTime, setStartAvailableTime] = useState('');
  const sportOptions = ['Football'];
  const accessToken = Cookies.get('accessToken');
  // 토큰에서 사용자 정보 추출
  let id =null;
  if (accessToken) {
    const decodedToken = jwtDecode(accessToken);
    id = Number(decodedToken.id);
  }
  // 폼 제출 핸들러
  const handleSubmit = (e) => {
    e.preventDefault();
    if (!selectedDate || !selectedSport || !startAvailableTime) {
      alert('모든 옵션을 선택해주세요.');
      return;
    }
    // 유저 데이터를 포함한 매칭 요청 데이터
    const requestData = {
        id: id,  // 유저 ID
        date: selectedDate,  // 선택한 날짜
        groupCode : null,
        sport: selectedSport,  // 선택한 스포츠
        time: startAvailableTime,  // 선택한 시간
      };
    console.log(requestData);
    // 매칭 요청 전송
    axios.post('http://localhost:8085/api/matching', requestData, {
      headers: {
        'Authorization': `Bearer ${accessToken}`,
        'Content-Type': 'application/json',
      },
      withCredentials: true, // 쿠키 포함
    })
    .then(response => {
      alert('매칭 요청이 성공적으로 전송되었습니다.');
      console.log('Match Response:', response.data);
    })
    .catch(error => {
      console.error('Error sending match request:', error);
    });
  };
  // 스포츠 종목 선택 핸들러
  const handleSportClick = (sport) => {
    setSelectedSport(sport);
  };
  return (
    <form onSubmit={handleSubmit}>
      <div className={styles.matching_option_item}>
        <label>날짜 선택:</label>
        <input
          type="date"
          value={selectedDate}
          onChange={(e) => setSelectedDate(e.target.value)}
          required
        />
      </div>
      <div className={styles.matching_option_item}>
        <label>경기시간:</label>
        <input
          type="time"
          value={startAvailableTime}
          onChange={(e) => setStartAvailableTime(e.target.value)}
          required
        />
      </div>
      <div className={styles.matching_option_item}>
        <label>스포츠 종목 선택:</label>
        <div>
          {sportOptions.map((sport) => (
            <button
              type="button"
              key={sport}
              onClick={() => handleSportClick(sport)}
              style={{
                margin: '5px',
                padding: '10px',
                backgroundColor: selectedSport === sport ? 'lightblue' : 'white',
                border: selectedSport === sport ? '2px solid blue' : '1px solid gray',
                cursor: 'pointer',
              }}
            >
              {sport}
            </button>
          ))}
        </div>
      </div>
      <button type="submit">랜덤 매칭</button>
    </form>
  );
}
export default MatchSetupSubPage;