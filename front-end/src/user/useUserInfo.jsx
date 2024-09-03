import { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import { jwtDecode } from 'jwt-decode';

const useUserInfo = () => {
  const [userInfo, setUserInfo] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUserInfo = async () => {
      const accessToken = Cookies.get('accessToken');

      console.log(accessToken);

      if (!accessToken) {
        setError(new Error('Access token not found'));
        setLoading(false);
        return;
      }

      try {
        const decodedToken = jwtDecode(accessToken);
        console.log(decodedToken);
        const identifier = decodedToken.Identifier;
        console.log(identifier);

        if (!identifier) {
          throw new Error('Identifier not found in access token');
        }

        const response = await axios.get('http://localhost:8085/api/user-info', {
          params: { identifier: identifier },
          headers: {
            'Authorization': `Bearer ${accessToken}` // 여기에 accessToken 추가
          },
          withCredentials: true // 쿠키를 포함하여 전송 (리프레시 토큰)
        });

        const { data, message, code, status } = response.data;

        console.log(data);

        if (code === 200) {
          setUserInfo(data);
        } else {
          setError(new Error(message || 'Error fetching user info'));
        }
      } catch (err) {
        console.error('Error fetching user info:', err);
        console.log("시발");
        setError(err);

        try {
          // 액세스 토큰에서 identifier 추출
          const accessToken = Cookies.get('accessToken');
          const decodedToken = accessToken ? jwtDecode(accessToken) : null;
          const identifier = decodedToken ? decodedToken.Identifier : null;

          console.log(identifier);
  
          // 새 액세스 토큰 발급 요청
          const response = await axios.post('http://localhost:8085/api/auth', { identifier }, {
            headers: {
              'Authorization': `Bearer ${accessToken}`,
              'Content-Type': 'application/json'
            },
            withCredentials: true // 쿠키를 자동으로 포함하도록 설정
          });

          console.log("성공");
  
          const newAccessToken = response.headers['authorization'].split(' ')[1]; // 응답 헤더에서 새 액세스 토큰 추출
          
          // 새로운 액세스 토큰과 리프레시 토큰을 쿠키와 axios에 설정
          Cookies.set('accessToken', newAccessToken, { path: '/' });

          console.log("ㄹㅇ 성공함");

          const userInfoResponse = await axios.get('http://localhost:8085/api/user-info', {
            params: { identifier: identifier },
            headers: {
              'Authorization': `Bearer ${accessToken}` // 여기에 accessToken 추가
            },
            withCredentials: true // 쿠키를 포함하여 전송 (리프레시 토큰)
          });
  
          const { data, message, code, status } = userInfoResponse.data;
  
          console.log(data);
  
          if (code === 200) {
            setUserInfo(data);
          } else {
            setError(new Error(message || 'Error fetching user info'));
          }

          
  
        } catch (refreshError) {
          console.error('Refresh token failed', refreshError);
          // 로그아웃 처리 또는 로그인 페이지로 리다이렉트
          // window.location.href = 'http://localhost:3000/login';
        }



      } finally {
        setLoading(false);
      }
    };

    fetchUserInfo();
  }, []);

  return { userInfo, loading, error };
};

export default useUserInfo;