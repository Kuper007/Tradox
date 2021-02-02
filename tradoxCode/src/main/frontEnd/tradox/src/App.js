import React from 'react';
import {Route, BrowserRouter} from 'react-router-dom';
import Register from './component/RegisterForm/Register.js';

const App = () => {
  return (
    <BrowserRouter>
      <div className = 'container-main'>
          <Register/>
      </div>
    </BrowserRouter>
  )
}

export default App
