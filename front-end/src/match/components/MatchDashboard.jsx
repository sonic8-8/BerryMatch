import styles from './MatchDashboard.module.css';
import useUserInfo from '../../user/useUserInfo';
import Cookies from 'js-cookie';
import axios from 'axios';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useState } from 'react';
import MatchComparison from '../test/MatchComparison';
function MatchDashboard() {

    const { userInfo, loading, error } = useUserInfo();

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className={styles.dashboard_container}>
            <div className={styles.dashboard_middle}>
                <div className={styles.dashboard_middle_menu_container}>
                    <MatchComparison />
                </div>
                <div className={styles.dashboard_middle_content}>
                    <Outlet />
                </div>
            </div>

        </div>
    );
}

export default MatchDashboard;

// private Long id;       // 유저 ID
// private String sport;  // 스포츠 종목
// private String time;   // 매칭 시간 (예: 14:00)
// private String groupCord;  // 그룹 코드
// private String date;   // 매칭 날짜 (예: 2024-08-23)