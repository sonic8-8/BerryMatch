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
import LogoutPage from './auth/pages/LogoutPage';

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />}/>
        <Route path="/login" element={<LoginPage />}/>
        <Route path="/logout" element={<LogoutPage/>} />
        <Route path="/signup" element={<SignupPage/>}/>
        <Route path="/rank" element={<RankPage/>} />
        <Route path="/mypage" element={<MyPage/>} />
        <Route path="/match" element={<MatchPage/>} />
        <Route path="/board" element={<BoardPage/>} />
        <Route path="/group/create" element={<GroupCreatePage/>} />
        <Route path="/group/search" element={<GroupSearchPage/>} />
        <Route path="/alert" element={<RankPage/>} />
        
      </Routes>
    </Router>
  );
}

export default App;
