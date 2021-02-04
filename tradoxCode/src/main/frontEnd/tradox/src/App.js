import React, {useState} from 'react';
import MainPage from './component/MainPage/MainPage';
import {Route, BrowserRouter} from 'react-router-dom';
import Register from './component/RegisterForm/Register';
import Auth from './component/Auth/Auth.js';
import FillDocs from './component/FillDocs/FillDocs.js';
import Verification from './component/Verification/Verification.js';

function App(){ 
  const[authorized, setAuthorized] = useState(false);

  return (
      <div className = 'container-main'>
        <BrowserRouter>
            <Route exact path  = '/'> <MainPage authorized = {authorized} /></Route>
            <Route path = '/registration'> <Register/></Route>
            <Route path = '/auth'> <Auth/> </Route>
            <Route path = '/docs'> <FillDocs/> </Route>
            <Route path = '/verification'> <Verification/> </Route>
        </BrowserRouter>
      </div>
  )
}



export default App
