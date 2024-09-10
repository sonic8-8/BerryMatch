import React from 'react';
import { Navigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import {jwtDecode} from 'jwt-decode';

const PrivateRoute = ({ children }) => {
    const token = Cookies.get('accessToken');
    const isAuthenticated = !!token;
    let userInfo = null;

    if (isAuthenticated) {
        try {
            // JWT 디코딩
            userInfo = jwtDecode(token);
            console.log('Decoded Token:', userInfo);  // 디코딩된 토큰 정보 확인
        } catch (error) {
            console.error('토큰 디코딩 오류:', error);
        }
    }

    return isAuthenticated ? (
        React.cloneElement(children, { userInfo })  // 사용자 정보를 MainPage에 전달
    ) : (
        <Navigate to="/login" replace />
    );
};

export default PrivateRoute;
