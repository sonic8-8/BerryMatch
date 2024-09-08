import React, { useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode'; // 명명된 내보내기로 수정
import Cookies from 'js-cookie';
import { EventSourcePolyfill } from 'event-source-polyfill'; // 올바르게 임포트

const Notification = () => {

    const [notifications, setNotifications] = useState([]);
    const accessToken = Cookies.get('accessToken');

    useEffect(() => {

        const decodedToken = jwtDecode(accessToken);
        const userId = decodedToken.id;

        console.log(userId);

        // 1번째 HTTP 통신에서만 인증이 필요함. 인증 후에는 연결이 유지됨.
        const eventSource = new EventSourcePolyfill('http://localhost:8085/api/stream', {
            params: { 'userId': userId },
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Accept': 'text/event-stream',
            },
            withCredentials: true
        });

        // 서버에서 메세지 받았을 경우
        eventSource.onmessage = (event) => {
            setNotifications((prevNotifications) => [...prevNotifications, event.data]);
        };

        // 에러 발생 시 연결 끊기
        eventSource.onerror = (error) => {
            console.error('SSE 오류:', error);
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, [accessToken]);

    return (
        <div>
            <h2>알림</h2>
            <ul>
                {notifications.map((notification, index) => (
                    <li key={index}>{notification}</li>
                ))}
            </ul>
        </div>
    );
};

export default Notification;