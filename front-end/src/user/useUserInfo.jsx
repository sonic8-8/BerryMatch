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

      const decodedToken = jwtDecode(accessToken);
      console.log(decodedToken);
      const identifier = decodedToken.Identifier;
      console.log(identifier);

      if (!accessToken) {
        setError(new Error('Access token not found'));
        setLoading(false);
        return;
      }

      if (!identifier) {
        throw new Error('Identifier not found in access token');
      }

      const apiResponse = '';
      const data = '';
      const message = ''; 
      const code = '';
      const status = '';

      try {
        const response = await axios.get('http://localhost:8085/api/user-info', {
          params: { identifier: identifier }
        });

        apiResponse = response.data;
        data = apiResponse.data;
        message = apiResponse.message;
        code = apiResponse.code;
        status = apiResponse.status;

        console.log(data);

        setUserInfo(data);
      } catch (err) {
        console.log(message);
      } finally {
        setLoading(false);
      }
    };

    fetchUserInfo();
  }, []);

  return { userInfo, loading, error };
};

export default useUserInfo;
