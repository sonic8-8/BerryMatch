import styles from './BoardPage.module.css';
import BoardDashboard from './components/BoardDashboard';

function BoardPage() {
    return (
        <div className={styles.layout_content}>

          <BoardDashboard/>

        </div>
      );
}

export default BoardPage;