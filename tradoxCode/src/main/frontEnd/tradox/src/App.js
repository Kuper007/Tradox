import React, {useState} from 'react';
import MainPage from './component/MainPage/MainPage';
import {Route, BrowserRouter} from 'react-router-dom';
import Register from './component/RegisterForm/Register';
function App(){ 
  const[registered, setRegister] = useState(false);
  return (
      <div className = 'container-main'>
        <BrowserRouter>
        <Route exact path  = '/'> <MainPage registered = {registered} /></Route>
        <Route path = '/registration'> <Register/></Route>
        </BrowserRouter>
      </div>
  )
}



export default App
