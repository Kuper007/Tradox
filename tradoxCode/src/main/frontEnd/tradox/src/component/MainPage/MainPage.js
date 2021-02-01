import React, { useState } from 'react';
import WorldMap from './WorldMap/WorldMap';
import Logo from './Logo/Logo';
import SearchBar from './SearchBar/SearchBar'
import myMap from "../CountrysMap.js"


function MainPage (props) {
  const [countryName, setCountryName] = useState('');
  const [countryId, setCountryId] = useState('');
  const [notFound, setNotFound] = useState(false)

  function getKeysFromMapArr(country) {
    let countryO = myMap.get(country)
    if(typeof countryO == "undefined"){
      setNotFound(true)
    }
    else{
      setNotFound(false)
    }
    return countryO
  }

  // const getCountryInfo = () => {
  //   const requestOptions = {
  //     method: 'POST',
  //     headers: { 'Content-Type': 'application/json' },
  //     body: JSON.stringify({ country: "'"+countryId+"'"})
  //   };
  //   fetch("http://localhost:8080/api/v1/route", requestOptions).then((res)=>{
  //     console.log(res);
  //     console.log(res.json());
  //   });
  // };

  function ifRegistered(){
    if(props.registered !== false){
      //getCountryInfo()
      console.log(countryId)
    }
  }
  function retrieveId(e){
    setCountryId(e.currentTarget.id)
    ifRegistered()
  }

  function handleCountry(e){
    setCountryName(e.target.value);
  }

  function handleKeyPress(e) {
    if (e.key === 'Enter') {
      setCountryId(getKeysFromMapArr(countryName))
      console.log(countryId)
    }
  }



  return (
      <div >
        <Logo/>
        <WorldMap retrieveId = {retrieveId}/>
        <SearchBar handleCountry = {handleCountry} handleKeyPress = {handleKeyPress} notfound = {notFound}/>
      </div>
  )
}

export default MainPage