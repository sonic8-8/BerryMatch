import React from 'react';
import useUserInfo from '../../user/useUserInfo';
import { Link, Outlet  } from 'react-router-dom';
import styles from './MainPage.module.css';
import GroupPopupPanel from '../../group/GroupPopupPanel';


function MainPage() {
  
  const { userInfo, loading, error } = useUserInfo();

  if (error) {
      return <div>Error: {error.message}</div>;
  }
  return (
    
      <div className={styles.layout}>
         <div className={styles.layout_header}>
              <div className={styles.layout_header_logo_container}>
                  <img className={styles.layout_header_logo} src='https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/design/logo.png'/>
                  <div className={styles.layout_header_logo_title}>BerryMatch</div>
              </div>
              <div className={styles.layout_header_user_container}>
                  <div className={styles.layout_header_identifier}>환영합니다! {userInfo ? userInfo.nickname : '' }님</div>
                  <Link to="/logout" className={styles.layout_header_logout}>
                          로그아웃
                  </Link>
              </div>
          </div>
        <div className={styles.layout_content}>
          <Outlet/>
        </div>
        <div className={styles.layout_footer}>
           <Link to="/" className={styles.layout_footer_menu}>Home</Link>
            <Link to="/alert" className={styles.layout_footer_menu}>알림</Link>
            <Link to="/mypage" className={styles.layout_footer_menu}>마이페이지</Link>
            <div><GroupPopupPanel/></div>
        </div>
      </div>

  );
};

export default MainPage;

