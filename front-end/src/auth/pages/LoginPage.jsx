import React from 'react';
import styles from './LoginPage.module.css'
import GoogleLoginButton from '../components/GoogleLoginButton';
import KakaoLoginButton from '../components/KakaoLoginButton';
import NaverLoginButton from '../components/NaverLoginButton';

const LoginPage = () => {

  return (

    <div className={styles.layout}>

      <header className={styles.layout_header}>
        
      </header>

      <content className={styles.layout_content}>

        <div className={styles.introduction_container}>

          <img className={styles.introduction_image} src="https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/design/login_image.jpg" />
          
          <div className={styles.introduction_text}>
            이번 경기의 주인공은
            <br/>
            바로 당신입니다.
            <br/>
            <span className={styles.introduction_text_berrymatch}>BerryMatch</span>
            <br/>
            매칭을 시작해보세요
          </div>

        </div>

        <div className={styles.login_container}>

          <div className={styles.login_text}>
            소셜 로그인으로
            <br/>
            빠르게
            <br/>
            시작해보세요!
          </div>
        
          <div className={styles.login_button_container}>
            <KakaoLoginButton />
            <NaverLoginButton />
            <GoogleLoginButton />
          </div>
        </div>
        
      </content>
      
      <footer className={styles.layout_footer}>

      </footer>

    </div>
  );
};

export default LoginPage;