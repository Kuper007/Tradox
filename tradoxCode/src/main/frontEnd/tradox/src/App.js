import React from 'react';
import MainPage from './component/MainPage/MainPage';
import Auth from './component/Auth/Auth';
import {Route, BrowserRouter} from 'react-router-dom';
import WorldMap from "./component/MainPage/WorldMap/WorldMap";
import Register from './component/RegisterForm/Register.js';

const App = () => {
  return (
    <BrowserRouter>
      <div className = 'container-main'>
       {/*<Auth/>*/}
       {/*//<MainPage/>*/}
         {/*<Route path = '' component = {RegisterForm}/> */}
         {/*<Register/>*/}
      </div>
    </BrowserRouter>
  )
}

export default App
