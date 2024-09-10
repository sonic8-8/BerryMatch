import React, { useState } from 'react';
import styles from './MatchPage.module.css';
import MatchDashboard from '../components/MatchDashboard';

function MatchPage() {
    return (

        <div className={styles.layout_content}>

            <MatchDashboard/>

        </div>
            

    )
}

export default MatchPage;