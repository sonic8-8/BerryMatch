import styles from './Dashboard.module.css';
import { Link } from 'react-router-dom';

function Dashboard() {

    return (
        <div className={styles.dashboard_container}>

            <div className={styles.dashboard_top}>
                <div>로고</div>
                <div>환영합니다! 홍길동님</div>
                <div>로그아웃</div>
            </div>

            <div className={styles.dashboard_middle}>

                <div className={styles.dashboard_middle_left}>
                    유저 정보 들어갈 자리
                    <image src="">프로필사진</image>
                    <div>전적</div>
                    <div>최근 경기</div>
                    
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


{/* <li className={styles.dashboard_button}>
<Link to="/" className={styles.dashboard_link}>로고</Link>
</li>
<li className={styles.dashboard_button}>
<Link to="/match" className={styles.dashboard_link}>매칭</Link>
</li>
<li className={styles.dashboard_button}>
<Link to="/board" className={styles.dashboard_link}>게시판</Link>
</li>
<li className={styles.dashboard_button}>
<Link to="/rank" className={styles.dashboard_link}>랭킹</Link>
</li>
<li className={styles.dashboard_button}>
<Link to="/user" className={styles.dashboard_link}>마이페이지</Link>
</li>
<li className={styles.dashboard_button}>
<Link to="/alert" className={styles.dashboard_link}>알림</Link>
</li>
<li className={styles.dashboard_button}>
<Link to="/logout" className={styles.dashboard_link}>로그아웃</Link>
</li>
<li className={styles.dashboard_button}>
<Link to="/group/search" className={styles.dashboard_link}>그룹찾기</Link>
</li>
<li className={styles.dashboard_button}>
<Link to="/group/generate" className={styles.dashboard_link}>그룹생성</Link>
</li> */}