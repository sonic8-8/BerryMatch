import React, { useEffect, useState } from 'react';
import { jwtDecode } from 'jwt-decode'; // 명명된 내보내기로 수정
import Cookies from 'js-cookie';
import { EventSourcePolyfill } from 'event-source-polyfill'; // 올바르게 임포트

const Notification = () => {
    const [notifications, setNotifications] = useState([]);
    const accessToken = Cookies.get('accessToken');

    useEffect(() => {
        const eventSource = new EventSourcePolyfill('http://localhost:8085/api/stream', {
            headers: {
                'Authorization': `Bearer ${accessToken}`,
                'Accept': 'text/event-stream',
                
            },
            withCredentials: true
        });

        eventSource.onmessage = (event) => {
            setNotifications((prevNotifications) => [...prevNotifications, event.data]);
        };

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