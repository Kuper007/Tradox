import React, {useState} from 'react';
import MainPage from './component/MainPage/MainPage';
import {Route, BrowserRouter} from 'react-router-dom';
import Register from './component/RegisterForm/Register';
import Auth from './component/Auth/Auth.js';
function App(){ 
  const[registered, setRegister] = useState(true);
  return (
      <div className = 'container-main'>
        <BrowserRouter>
        <Route exact path  = '/'> <MainPage registered = {registered} /></Route>
        <Route path = '/registration'> <Register/></Route>
        <Route path = '/auth'> <Auth/></Route>
        </BrowserRouter>
      </div>
  )
}



export default App
