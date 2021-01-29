import React, { useState } from 'react';
import WorldMap from './WorldMap/WorldMap';
import Logo from './Logo/Logo';
import SearchBar from './SearchBar/SearchBar'
import myMap from "../CountrysMap.js"
import UnregisteredUserNotification from '../UnauthorizedUserNotification/UnauthorizedUserNotification'

function MainPage (props) {
  const[registered, setRegister] = useState(true);
  const [countryName, setState] = useState('');
  const[countryId, setTitle] = useState('');

  function getKeysFromMapArr(country) {
    let countryO = myMap.get(country)
    if(typeof countryO == "undefined"){
    }
    return countryO
  }

  function ifRegistered(){
    if(props.registered === false){
      setRegister(false)
    }
    else{
      console.log(countryId)
    }
  }
  function retrieveId(e){    
    setTitle(e.target.id) 
    ifRegistered()  
  }
  
  function handleCountry(e){
    setState(e.target.value);
   }

  function handleKeyPress(e) {
    if (e.key === 'Enter') {
      console.log(getKeysFromMapArr(countryName))
    }
  }


  
  return (
    <div >
      <Logo/>
      <WorldMap retrieveId = {retrieveId}/>
      <SearchBar handleCountry = {handleCountry} handleKeyPress = {handleKeyPress}/>
      {!registered ? <UnregisteredUserNotification/>:null}
    </div>
  )
}

export default MainPage