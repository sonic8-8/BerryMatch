import React, { useState } from 'react';
import styles from './MatchLobbyPage.module.css';
import MatchLobby from '../components/MatchLobby';


function MatchLobbyPage() {

    return (

        <div className={styles.layout}>
          
          <div className={styles.layout_header}>
            

          </div>
    
          <div className={styles.layout_content}>

            <MatchLobby/>
            
          </div>
          
          <div className={styles.layout_footer}>


          </div>
    
        </div>
      );
    

}

export default MatchLobbyPage;