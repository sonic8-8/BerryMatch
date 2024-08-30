import React from 'react';
import axios from "axios";
import styles from './MainPage.module.css'
import dashboard from '../../common/dashboard';

function MainPage() {

  return (
    
      <div className={styles.layout}>
        

        <header className={styles.layout_header}>
          <dashboard />
        </header>

        <content className={styles.layout_content}>


        </content>
        
        <footer className={styles.layout_footer}>


        </footer>

      </div>

  );
};

export default MainPage;

