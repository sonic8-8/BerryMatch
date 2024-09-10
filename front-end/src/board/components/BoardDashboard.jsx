import styles from './BoardDashboard.module.css';
import useUserInfo from '../../user/useUserInfo';
import Cookies from 'js-cookie';
import axios from 'axios';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useParams } from 'react-router-dom';

function BoardDashboard() {

    const { userInfo, loading, error } = useUserInfo();
    const { currentPage } = useParams();

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className={styles.dashboard_middle_content}>
            <Outlet />
        </div>
    );
}

export default BoardDashboard;

// private Long id;       // 유저 ID
// private String sport;  // 스포츠 종목
// private String time;   // 매칭 시간 (예: 14:00)
// private String groupCord;  // 그룹 코드
// private String date;   // 매칭 날짜 (예: 2024-08-23)