import styles from './Navbar.module.css';
import { Link } from 'react-router-dom';

function dashboard() {

    return (
        <nav className={styles.navigation_container}>
            <ul className={styles.nav}>
                <li className={styles.navbar_button}>
                    <Link to="/" className={styles.navbar_link}>로고</Link>
                </li>
                <li className={styles.navbar_button}>
                    <Link to="/about" className={styles.navbar_link}>매칭</Link>
                </li>
                <li className={styles.navbar_button}>
                    <Link to="/board" className={styles.navbar_link}>게시판</Link>
                </li>
                <li className={styles.navbar_button}>
                    <Link to="/rank" className={styles.navbar_link}>랭킹</Link>
                </li>
                <li className={styles.navbar_button}>
                    <Link to="/user" className={styles.navbar_link}>마이페이지</Link>
                </li>
                <li className={styles.navbar_button}>
                    <Link to="/user" className={styles.navbar_link}>알림</Link>
                </li>
                <li className={styles.navbar_button}>
                    <Link to="/user" className={styles.navbar_link}>로그아웃</Link>
                </li>
                <li className={styles.navbar_button}>
                    <Link to="/user" className={styles.navbar_link}>그룹찾기</Link>
                </li>
                <li className={styles.navbar_button}>
                    <Link to="/user" className={styles.navbar_link}>그룹생성</Link>
                </li>
            </ul>
        </nav>
    );
}

export default dashboard;