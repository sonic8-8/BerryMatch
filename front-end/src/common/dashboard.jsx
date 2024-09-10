import styles from './Dashboard.module.css';
import useUserInfo from '../user/useUserInfo';
import { Link } from 'react-router-dom';
import MatchSetupSubPage from '../match/pages/MatchSetupSubPage';
import MatchStatus from './components/MatchStatus';

function Dashboard() {

    const { userInfo, loading, error } = useUserInfo();

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className={styles.dashboard_container}>
                <div className={styles.dashboard_content_left}>
                    <div className={styles.dashboard_content_left_item}>
                        <img className={styles.dashboard_content_left_content_profile_image} src={userInfo ? userInfo.profileImageUrl : ''} />
                    </div>
                    <div className={styles.dashboard_content_left_item}>닉네임 : {userInfo ? userInfo.nickname : '' }</div>
                    <div className={styles.dashboard_content_left_item}>자기소개 : {userInfo ? userInfo.introduction : ''}</div>
                    <div className={styles.dashboard_content_left_item}>전적 : </div>
                    <div className={styles.dashboard_content_left_item}>설정 주소 : {userInfo ? userInfo.city + ' ' + userInfo.district : ''}</div>
                </div>

                <div className={styles.dashboard_content_right}>
                    <div className={styles.dashboard_content_right_menu_container}>
                        <div className={styles.dashboard_content_right_menu_large}>
                            <MatchStatus/>
                            <MatchSetupSubPage />
                        </div>
                    </div>
                    <div className={styles.dashboard_content_right_menu_container}>
                        <Link to="/rank" className={styles.dashboard_content_right_menu_small}>랭킹</Link>
                        <Link to="/board" className={styles.dashboard_content_right_menu_small}>하이라이트</Link>
                    </div>
                    <div className={styles.dashboard_content_right_menu_container}>
                        <Link to="/guild" className={styles.dashboard_content_right_menu_large}>길드</Link>
                    </div>
                </div>
            </div>
     
    );
}

export default Dashboard;