import React from 'react';
import styles from './LoginPage.module.css'
import GoogleLoginButton from '../components/GoogleLoginButton';
import KakaoLoginButton from '../components/KakaoLoginButton';
import NaverLoginButton from '../components/NaverLoginButton';

const LoginPage = () => {

  return (

    <div className={styles.layout}>

      <div className={styles.layout_header}>
        
      </div>

      <div className={styles.layout_content}>

        <div className={styles.introduction_container}>

          <img className={styles.introduction_image} src="https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/design/login_image.jpg" />
          
          <div className={styles.introduction_text}>
            혼자라서 더
            <br/>
            특별한 도전
            <br/>
            <span className={styles.introduction_text_berrymatch}>BerryMatch</span>
            <br/>
            랜덤 매칭으로
            <br/>
            당신의 실력을 증명하세요
          </div>

        </div>

        <div className={styles.login_container}>

          <div className={styles.login_text_container}>
            <div className={styles.login_text}>
              소셜 로그인으로
              <br/>
              빠르게
              <br/>
              시작해보세요
            </div>
          </div>
        
          <div className={styles.login_button_container}>
            <KakaoLoginButton />
            <NaverLoginButton />
            <GoogleLoginButton />
          </div>
        </div>
        
      </div>
      
      <div className={styles.layout_footer}>

      </div>

    </div>
  );
};

export default LoginPage;