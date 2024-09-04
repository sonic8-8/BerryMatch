import React, { useState } from 'react';
import styles from './MatchSetupSubPage.module.css';
import MatchDashboard from '../components/MatchDashboard';

function MatchSetupSubPage() {
    return (

        <div className={styles.layout}>
          
            <input placeholder='조건 입력받기'></input>
            <input placeholder='조건 입력받기'></input>
            <input placeholder='조건 입력받기'></input>
            <input placeholder='조건 입력받기'></input>
            <button>매칭</button>
  
      </div>

    )
}

export default MatchSetupSubPage;