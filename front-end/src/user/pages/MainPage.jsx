import React from 'react';
import axios from "axios";
import styles from './MainPage.module.css'
import Dashboard from '../../common/Dashboard'
import BackgroundMusic from '../../common/components/BackgroundMusic';

function MainPage() {

  return (
    
      <div className={styles.layout}>
        
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

