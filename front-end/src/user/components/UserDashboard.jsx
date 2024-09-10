import styles from './UserDashboard.module.css';
import useUserInfo from '../useUserInfo';
import { Link, Outlet, Route, Routes, useNavigate } from 'react-router-dom';
import PrivateRoute from '../../auth/components/PrivateRoute';
import ProfileEditSubPage from '../pages/ProfileEditSubPage';
import MatchResultsSubPage from '../pages/MatchResultsSubPage';
import AccountDeletionSubPage from '../pages/AccountDeletionSubPage';

function UserDashboard() {

    const { userInfo, loading, error } = useUserInfo();

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
            
            <div className={styles.dashboard_middle}>
                <div className={styles.dashboard_middle_menu_container}>
                    <Link to="profile-edit" className={styles.dashboard_middle_menu}>프로필 수정</Link>
                    <Link to="address-edit" className={styles.dashboard_middle_menu}>주소 변경</Link>
                    <Link to="match-results" className={styles.dashboard_middle_menu}>경기 기록</Link>
                    <Link to="account-deletion" className={styles.dashboard_middle_menu}>회원 탈퇴</Link>
                </div>
                <div className={styles.dashboard_middle_content}>
                    <Outlet/>
                </div>
            </div>
    );
}

export default UserDashboard;

// private Long id;       // 유저 ID
// private String sport;  // 스포츠 종목
// private String time;   // 매칭 시간 (예: 14:00)
// private String groupCord;  // 그룹 코드
// private String date;   // 매칭 날짜 (예: 2024-08-23)