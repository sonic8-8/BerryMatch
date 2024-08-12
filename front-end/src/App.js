import React, { useState } from 'react';
import axios from 'axios';

import './App.css';

function App() {
  const [data, setData] = useState('여기에 데이터 받아올거임');
  const [error, setError] = useState(null);


  const fetchData = async () => {
    try {
      const response = await axios.get('/api/hello');
      setData(response.data.응답);
      setError(null);
    } catch (error) {
      setError('통신 실패함');
    }
  };

  return (
    <div className="container">
      <div>
        <h4>안녕하세요</h4>
        <button onClick={fetchData}>서버에 요청보내기</button>
        <p>{error ? error : data}</p>
      </div>
    </div>
  );
}

export default App;
