import React from 'react';
import axios from "axios";
import styles from './MainPage.module.css'
import Dashboard from '../../common/Dashboard'
import BackgroundMusic from '../../common/components/BackgroundMusic';

function MainPage() {

  return (
    
      <div className={styles.layout}>

        <video autoPlay muted loop className={styles.backgroundVideo}>
          <source src="https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/design/main_background_video.mp4" type="video/mp4" />
        </video>
        
        <div className={styles.layout_header}>
          <BackgroundMusic />
        </div>

        <div className={styles.layout_content}>

          <Dashboard />
          
          
        </div>
        
        <div className={styles.layout_footer}>


        </div>

      </div>

  );
};

export default MainPage;

