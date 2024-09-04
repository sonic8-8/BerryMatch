import styles from './Dashboard.module.css';
import useUserInfo from '../user/useUserInfo';
import Cookies from 'js-cookie';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { useState } from 'react';


function Dashboard() {

    const { userInfo, loading, error } = useUserInfo();

    if (loading) {
        return <div>로딩중...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className={styles.dashboard_container}>

            <div className={styles.dashboard_top}>
                <div className={styles.dashboard_top_logo_container}>
                    <img className={styles.dashboard_top_logo} src='https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/design/logo.png'/>
                    <div className={styles.dashboard_top_logo_title}>BerryMatch</div>
                </div>
                <div className={styles.dashboard_top_identifier}>환영합니다! {userInfo ? userInfo.nickname : '' }님</div>
                <Link to="/logout" className={styles.dashboard_top_logout}>
                        로그아웃
                </Link>
                
            </div>

            <div className={styles.dashboard_middle}>

                <div className={styles.dashboard_middle_left}>
                    <div className={styles.dashboard_middle_left_content}>
                        프로필사진
                        <img src={userInfo ? userInfo.profileImageUrl : ''} />
                    </div>
                    <div className={styles.dashboard_middle_left_content}>닉네임 : {userInfo ? userInfo.nickname : '' }</div>
                    <div className={styles.dashboard_middle_left_content}>자기소개 : {userInfo ? userInfo.introduction : ''}</div>
                    <div className={styles.dashboard_middle_left_content}>전적</div>
                    <div className={styles.dashboard_middle_left_content}>최근 경기</div>
                    <div></div>
                    
                </div>

                <div className={styles.dashboard_middle_right}>
                    <div className={styles.dashboard_middle_right_menu_container}>
                        <Link to="/match" className={styles.dashboard_middle_right_menu}>매칭</Link>
                    </div>
                    <div className={styles.dashboard_middle_right_menu_container}>
                        <Link to="/rank" className={styles.dashboard_middle_right_menu}>랭킹</Link>
                        <Link to="/board" className={styles.dashboard_middle_right_menu}>하이라이트 게시판</Link>
                    </div>
                    <div className={styles.dashboard_middle_right_menu_container}>
                        <Link to="/guild" className={styles.dashboard_middle_right_menu}>길드</Link>
                        <Link to="/group" className={styles.dashboard_middle_right_menu}>그룹찾기/그룹생성</Link>
                    </div>

                </div>

            </div>

            <div className={styles.dashboard_bottom}>
                <Link to="/" className={styles.dashboard_bottom_menu}>Home</Link>
                <Link to="/alert" className={styles.dashboard_bottom_menu}>알림</Link>
                <Link to="/mypage" className={styles.dashboard_bottom_menu}>마이페이지</Link>
                <div className={styles.dashboard_bottom_menu}>그룹정보</div>
            </div>

        </div>
    );
}

export default Dashboard;