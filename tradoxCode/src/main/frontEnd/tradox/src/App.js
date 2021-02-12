import React, {useState} from 'react';
import MainPage from './component/MainPage/MainPage';
import {Route, BrowserRouter} from 'react-router-dom';
import Register from './component/RegisterForm/Register';
import Auth from './component/Auth/Auth.js';
import FillDocs from './component/FillDocs/FillDocs.js';
import Verification from './component/Verification/Verification.js';
import PasswordReset from './component/PasswordReset/PasswordReset.js';
import Admin from './component/Admin/Admin.js';
import Account from './component/Account/Account.js';

function App(){ 

  return (
      <div className = 'container-main'>
        <BrowserRouter>
            <Route exact path  = '/'> <MainPage/></Route>
            <Route path = '/registration'> <Register/></Route>
            <Route path = '/auth'> <Auth/> </Route>
            <Route path = '/docs'> <FillDocs/> </Route>
            <Route path = '/verification'> <Verification/> </Route>
            <Route path = '/reset'> <PasswordReset/> </Route>
            <Route path = '/admin'> <Admin/> </Route>
            <Route path = '/account'> <Account/> </Route>
        </BrowserRouter>
      </div>
  )
}



export default App
