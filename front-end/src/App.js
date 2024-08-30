import React, { useState } from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import styles from './App.module.css';
import VideoUpload from './VideoUpload';
import LoginPage from './auth/pages/LoginPage';
import MainPage from './user/pages/MainPage';
import GoogleLoginButton from './auth/components/GoogleLoginButton';
import SignupPage from './auth/pages/SignupPage';

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainPage />}/>
        <Route path="/login" element={<LoginPage />}/>
        <Route path="/signup" element={<SignupPage/>}/>
      </Routes>
    </Router>
  );
}

export default App;
