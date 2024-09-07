import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import AppWrapper from './App';
import reportWebVitals from './reportWebVitals';
import { NavermapsProvider } from 'react-naver-maps';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    
    // <React.StrictMode>
    <NavermapsProvider >
      <AppWrapper />
    </NavermapsProvider>
    // </React.StrictMode>

);

reportWebVitals();
