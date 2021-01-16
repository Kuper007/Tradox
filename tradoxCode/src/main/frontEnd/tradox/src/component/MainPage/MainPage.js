import React from 'react';
import WorldMap from './WorldMap/WorldMap';
import Logo from './Logo/Logo';
import SearchBar from './SearchBar/SearchBar'
import UnregisteredUserNotification from '../UnregisteredUserNotification/UnregisteredUserNotification';
import {Route, BrowserRouter} from 'react-router-dom';

const MainPage = () => {
  return (
    <BrowserRouter>    
    <div >
      <Logo/>
      <WorldMap/>
      <SearchBar/>
      {/*<Route path = '/unregistered' component = {UnregisteredUserNotification}/>*/}
    </div>
    </BrowserRouter>

  )
}

export default MainPage