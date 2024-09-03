import React, { useState } from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import styles from './App.module.css';
import VideoUpload from './VideoUpload';
import LoginPage from './auth/pages/LoginPage';
import MainPage from './user/pages/MainPage';
import SignupPage from './auth/pages/SignupPage';
import MyPage from './user/pages/MyPage';
import RankPage from './rank/RankPage';
import GroupCreatePage from './group/GroupCreatePage';
import GroupSearchPage from './group/GroupSearchPage';
import BoardPage from './board/BoardPage';
import MatchPage from './match/MatchPage';
import LogoutPage from './auth/pages/LogoutPage'
import PrivateRoute from './auth/components/PrivateRoute';
import TokenPage from './auth/pages/TokenPage';

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<PrivateRoute><MainPage /></PrivateRoute>} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/logout" element={<LogoutPage />} />
        <Route path="/token" element={<TokenPage/>} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/rank" element={<PrivateRoute><RankPage /></PrivateRoute>} />
        <Route path="/mypage" element={<PrivateRoute><MyPage /></PrivateRoute>} />
        <Route path="/match" element={<PrivateRoute><MatchPage /></PrivateRoute>} />
        <Route path="/board" element={<PrivateRoute><BoardPage /></PrivateRoute>} />
        <Route path="/group/create" element={<PrivateRoute><GroupCreatePage /></PrivateRoute>} />
        <Route path="/group/search" element={<PrivateRoute><GroupSearchPage /></PrivateRoute>} />
        <Route path="/alert" element={<PrivateRoute><MainPage /></PrivateRoute>} />
      </Routes>
    </Router>
  );
}

export default App;
