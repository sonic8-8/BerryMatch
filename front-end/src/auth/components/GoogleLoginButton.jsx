import React from 'react';
import styles from './GoogleLoginButton.module.css';

function GoogleLoginButton() {

    // const handleGoogleLogin = () => {
    //     window.location.href = `https://accounts.google.com/o/oauth2/auth?client_id=${process.env.REACT_APP_GOOGLE_OAUTH2_CLIENT_ID}&redirect_uri=${process.env.REACT_APP_OAUTH2_REDIRECT_URI}/login/oauth2/code/google&response_type=code&scope=email%20profile`;
    // };

    const handleGoogleLogin = () => {
        window.location.href = "http://localhost:8085/oauth2/authorization/google";
    };

    return (
        <div className={styles.button} onClick={handleGoogleLogin}>
            <img src="https://d1nuzc1w51n1es.cloudfront.net/d99d8628713bb69bd142.png" className={styles.button_img} alt="Google Login"/>
            <span className={styles.button_title}>google</span>
        </div>
    );
}


export default GoogleLoginButton;