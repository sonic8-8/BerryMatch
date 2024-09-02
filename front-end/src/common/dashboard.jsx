import styles from './Dashboard.module.css';
import { Link } from 'react-router-dom';
import useUserInfo from '../user/useUserInfo';

function Dashboard() {

    const { userInfo, loading, error } = useUserInfo();

    if (loading) {
      return <div>로딩중</div>;
    }
  
    if (error) {
      return <div>Error: {error.message}</div>;
    }
    

    return (
        <div className={styles.dashboard_container}>

            <div className={styles.dashboard_top}>
                <div className={styles.dashboard_top_logo_container}>
                    <img className={styles.dashboard_top_logo} src='https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/design/logo.png'/>
                </div>
                {/* <div className={styles.dashboard_top_identifier}>환영합니다! {userInfo ? userInfo.nickname : loading }님</div> */}
                <div className={styles.dashboard_top_identifier}>환영합니다! OO님</div>
                <div className={styles.dashboard_top_logout}>로그아웃</div>
            </div>

            <div className={styles.dashboard_middle}>

                <div className={styles.dashboard_middle_left}>
                    {/* <img src={userInfo ? userInfo.profileImageUrl : ''}  className={styles.dashboard_middle_left_content} /> */}
                    {/* <div>{userInfo ? userInfo.nickname : loading }</div> */}
                    <div className={styles.dashboard_middle_left_content}>전적</div>
                    <div className={styles.dashboard_middle_left_content}>최근 경기</div>
                    
                </div>

                <div className={styles.dashboard_middle_right}>
                    <div className={styles.dashboard_middle_right_menu_container}>
                        <div className={styles.dashboard_middle_right_menu}>매칭</div>
                    </div>
                    <div className={styles.dashboard_middle_right_menu_container}>
                        <div className={styles.dashboard_middle_right_menu}>랭킹</div>
                        <div className={styles.dashboard_middle_right_menu}>하이라이트 게시판</div>
                    </div>
                    <div className={styles.dashboard_middle_right_menu_container}>
                        <div className={styles.dashboard_middle_right_menu}>그룹찾기</div>
                        <div className={styles.dashboard_middle_right_menu}>그룹생성</div>
                    </div>

                </div>

            </div>

            <div className={styles.dashboard_bottom}>
                <div className={styles.dashboard_bottom_menu}>Home</div>
                <div className={styles.dashboard_bottom_menu}>알림</div>
                <div className={styles.dashboard_bottom_menu}>마이페이지</div>
                <div className={styles.dashboard_bottom_menu}>그룹정보</div>
            </div>

        </div>
    );
}

export default Dashboard;