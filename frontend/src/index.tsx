import React from 'react';
import ReactDOM from 'react-dom/client'; // Updated import
import App from './components/App';
import './index.css';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
    <App />
);
