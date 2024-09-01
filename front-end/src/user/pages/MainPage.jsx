import React from 'react';
import axios from "axios";
import styles from './MainPage.module.css'
import Dashboard from '../../common/Dashboard';

function MainPage() {

  return (
    
      <div className={styles.layout}>
        
        <header className={styles.layout_header}>
          
        </header>

        <content className={styles.layout_content}>

          <Dashboard />
          
        </content>
        
        <footer className={styles.layout_footer}>


        </footer>

      </div>

  );
};

export default MainPage;

