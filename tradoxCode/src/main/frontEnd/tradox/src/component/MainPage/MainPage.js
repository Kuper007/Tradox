import React, { useState, useEffect } from 'react';
import WorldMap from './WorldMap/WorldMap';
import Logo from './Logo/Logo';
import SearchBar from './SearchBar/SearchBar';
import myMap from "../CountrysMap.js";
import Country from '../CountryInfo/CountryInfo';
import UnauthorizedUserNotification from "../UnauthorizedUserNotification/UnauthorizedUserNotification";
import NoData from '../CountryInfo/NoData/NoData'
import axios from "axios";

function MainPage (props) {
  const [destinationName, setDestinationName] = useState('');
  const [destinationId, setDestinationId] = useState('');
  const [notFound, setNotFound] = useState(false);
  const [pressed, setPressed] = useState(false)
  const [data, setData] = useState([])
  useEffect(() => {
    ifRegistered()
  }, [destinationId])

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
  function getKeyFromMap(){
    let keys = [...myMap.entries()]
        .filter(({ 1: v }) => v === destinationId)
        .map(([k]) => k);
    setDestinationName(keys)
  }
  function getCountryInfo(){
    try {
      const response = axios.post('http://localhost:8080/api/v1/route/routing', { departureId: "'"+"UA"+"'", destinationId: "'"+destinationId+"'" });
      console.log('👉 Returned data:', `${response.data}`);
      if(response.status !== 200){
        return <NoData/>
      }
      else{
        console.log(`${response.data}`)
      }
    } catch (e) {
      console.log(`😱 Axios request failed: ${e}`);
    }

  }

  function ifRegistered(){
    if(destinationId !== ''){
      if(props.authorized !== false){
        getKeyFromMap()
        setPressed(true)
        console.log(getCountryInfo())
    }
      else{
        return <UnauthorizedUserNotification/>
      }
    }
  }

  function retrieveId(e){
    setDestinationId(e.currentTarget.id)
  }

  function handleCountry(e){
    setDestinationName(e.target.value);
  }

  function handleKeyPress(e) {
    if (e.key === 'Enter') {
      setDestinationId(getKeysFromMapArr(destinationName))
    }
  }



  return (
      <div >
        <Logo authorized = {props.authorized}/>
        <WorldMap retrieveId = {retrieveId}/>
        {!pressed?<SearchBar handleCountry = {handleCountry} handleKeyPress = {handleKeyPress} notfound = {notFound}/>:null}
        {pressed?<Country country = {destinationName}/>:null}
      </div>
  )
}

export default MainPage