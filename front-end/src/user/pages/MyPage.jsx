import styles from './MyPage.module.css';
import UserDashboard from '../components/UserDashboard';

function Mypage() {
    return (
        <div className={styles.layout_content}>
          <UserDashboard/>
        </div>
      );
}

export default Mypage;
