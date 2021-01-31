import React, { useState } from 'react';
import WorldMap from './WorldMap/WorldMap';
import Logo from './Logo/Logo';
import SearchBar from './SearchBar/SearchBar'
import myMap from "../CountrysMap.js"
import UnregisteredUserNotification from '../UnauthorizedUserNotification/UnauthorizedUserNotification'


function MainPage (props) {
  const [countryName, setCountryName] = useState('');
  const[countryId, setCountryId] = useState('');
  const[showNotification, setShowNotification] = useState(false)

  function getKeysFromMapArr(country) {
    let countryO = myMap.get(country)
    if(typeof countryO == "undefined"){
    }
    return countryO
  }

//   const getCountry = () => {
//     const requestOptions = {
//         method: 'POST',
//         headers: { 'Content-Type': 'application/json' },
//         body: JSON.stringify({ country: "'"+countryId+"'"})
//     };
//     fetch("http://localhost:8080/api/v1/", requestOptions).then((res)=>{
//         console.log(res);
//         console.log(res.json());
//     });
// };
  function retrieveId(e){
    if(props.registered === false){
      setShowNotification(true)
    }
    else {
      setCountryId(e.currentTarget.id)
    }
  }
  function showId(){
    console.log(countryId)
  }
  function handleCountry(e){
    setCountryName(e.target.value);
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
      {showNotification ? <UnregisteredUserNotification/>:null}
      <button onKeyPress={showId}></button>
    </div>
  )
}

export default MainPage