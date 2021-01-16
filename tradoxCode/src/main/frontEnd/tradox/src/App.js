import React from 'react';
import MainPage from './component/MainPage/MainPage';
import {Route, BrowserRouter} from 'react-router-dom';
import WorldMap from "./component/MainPage/WorldMap/WorldMap";

const App = () => {
  return (
    <BrowserRouter>
      <div className = 'container-main'>
        <MainPage/>
         {/*<Route path = '' component = {RegisterForm}/> */}
      </div>
    </BrowserRouter>
  )
}



export default App
