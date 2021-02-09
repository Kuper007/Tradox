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
  let fail = false;
  let showNoAuth = false;
  const [showInfo, setShowInfo] = useState(false);
  const [destinationName, setDestinationName] = useState('');
  const [destinationId, setDestinationId] = useState('');
  const [notFound, setNotFound] = useState(false);
  const [isAuth, setIsAuth] = useState(true);
  const [data, setData] = useState([]);


  useEffect(() => {
    ifRegistered()
  }, [destinationId])

  useEffect(() => {
      if(localStorage.getItem("auth")){
        let userId = localStorage.getItem("userId");
        setIsAuth(true);
      }
  });

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
      try{
        axios.post("http://localhost:8080/api/v1/route/getCountryInfo", { "countryName": destinationId}, {headers:{ 'Content-Type': 'application/json' }}).then(res => {
          setData(res.data)
          if (res.status === 200){
            setShowInfo(true)
          }
        })
      }
      catch (e){
        console.log(`😱 Axios request failed: ${e}`);
      }
    }

  function ifRegistered(){
    if(destinationId != ''){
      if(isAuth != false){
        getKeyFromMap()
        getCountryInfo()
    }
      else{
          return showNoAuth = true;
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
        {!showInfo?<SearchBar handleCountry = {handleCountry} handleKeyPress = {handleKeyPress} notfound = {notFound}/>:null}
        {showInfo?<Country country = {destinationName} data = {data}/>:null}
        {fail?<NoData/>:null}
        {showNoAuth?<UnauthorizedUserNotification/>:null}
      </div>
  )
}

export default MainPage