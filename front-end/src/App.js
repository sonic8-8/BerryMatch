import React from 'react';
import { BrowserRouter as Router, useLocation, Route, Routes } from 'react-router-dom';
import styles from './App.module.css';
import LoginPage from './auth/pages/LoginPage';
import MainPage from './common/pages/MainPage';
import SignupPage from './auth/pages/SignupPage';
import LogoutPage from './auth/pages/LogoutPage';
import PrivateRoute from './auth/components/PrivateRoute';
import TokenPage from './auth/pages/TokenPage';
import BackgroundMusic from './common/components/BackgroundMusic';
import Dashboard from './common/Dashboard';
import RankPage from './rank/RankPage';
import Mypage from './user/pages/MyPage';
import MatchPage from './match/pages/MatchPage';
import MatchComparison from './match/test/MatchComparison';
import PostList from './board/PostList';
import BoardPage from './board/BoardPage';
import PostWritePage from './board/pages/PostWritePage';
import GroupSearchPage from './group/GroupSearchPage';
import GroupCreatePage from './group/GroupCreatePage';
import Map from './common/Map';
import ProfileEditSubPage from './user/pages/ProfileEditSubPage';
import AccountDeletionSubPage from './user/pages/AccountDeletionSubPage';
import MatchResultsSubPage from './user/pages/MatchResultsSubPage';

function App() {
  const location = useLocation();

  // 특정 경로에서만 배경 비디오와 음악을 표시
  const showBackground = location.pathname.startsWith('/') && !['/login', '/signup', '/token', '/logout'].includes(location.pathname);

  return (
    <div className={styles.layout}>
      {/* 조건부 배경 비디오 */}
      {showBackground && (
        <video autoPlay muted loop className={styles.backgroundVideo}>
          <source src="https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/design/main_background_video.mp4" type="video/mp4" />
        </video>
      )}
      
      {/* 조건부 배경 음악 */}
      {showBackground && <BackgroundMusic />}

      <Routes>
        <Route path="/" element={<PrivateRoute><MainPage /></PrivateRoute>}>
          <Route path="/" element={<Dashboard />} />
        
          <Route path="/rank" element={<RankPage />} />
          
          <Route path="/mypage" element={<Mypage />} >
            <Route path="profile-edit" element={<ProfileEditSubPage />} />
            <Route path="match-results" element={<MatchResultsSubPage />} />
            <Route path="account-deletion" element={<AccountDeletionSubPage />} />
          </Route>
          
          <Route path="/match" element={<MatchPage />} />
          
          <Route path="/match/lobby" element={<MatchComparison />} />
          
          <Route path="/board" element={<BoardPage />}>
            <Route path=":currentPage" element={<PostList />} />
            <Route path="post/write" element={<PostWritePage />} />
          </Route>
          
          <Route path="/group/create" element={<GroupCreatePage />} />
          <Route path="/group/search" element={<GroupSearchPage />} />
          
          <Route path="/map" element={<Map />} />
        </Route>

        <Route path="/login" element={<LoginPage />} />
        <Route path="/logout" element={<LogoutPage />} />
        <Route path="/token" element={<TokenPage />} />
        <Route path="/signup" element={<SignupPage />} />
      </Routes>
    </div>
  );
}

export default function AppWrapper() {
  return (
    <Router>
      <App />
    </Router>
  );
}
