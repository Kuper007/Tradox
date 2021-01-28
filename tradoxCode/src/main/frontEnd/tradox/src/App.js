import React from 'react';
import MainPage from './component/MainPage/MainPage';
import Auth from './component/Auth/Auth';
import FillDocs from './component/FillDocs/FillDocs';
import Register from './component/RegisterForm/Register';
import {Route, BrowserRouter} from 'react-router-dom';
import WorldMap from "./component/MainPage/WorldMap/WorldMap";

const App = () => {
  return (
    <BrowserRouter>
      <div className = 'container-main'>
       <FillDocs/>
      </div>
    </BrowserRouter>
  )
}

export default App
