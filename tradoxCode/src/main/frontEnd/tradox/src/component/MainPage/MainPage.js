import React, { useState, useEffect } from 'react';
import WorldMap from './WorldMap/WorldMap';
import Logo from './Logo/Logo';
import SearchBar from './SearchBar/SearchBar';
import myMap from "../CountrysMap.js";
import CountryInfo from '../CountryInfo/CountryInfo';
import UnauthorizedUserNotification from "../UnauthorizedUserNotification/UnauthorizedUserNotification";
import NoData from '../CountryInfo/NoData/NoData'
import axios from "axios";
import Spinner from 'react-spinner-material';

function MainPage (props) {
  const [loader, setLoader] = useState(false);
  const [failed, setFailed] = useState(false);
  const [showUnauthorized, setShowUnauthorized] = useState(false);
  const [showInfo, setShowInfo] = useState(false);
  const [destinationName, setDestinationName] = useState('');
  const [destinationId, setDestinationId] = useState('');
  const [notFound, setNotFound] = useState(false);
  const [isAuth, setIsAuth] = useState(false);
  const [data, setData] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    setNotFound(false)
  },[destinationName])

  useEffect(() => {
    ifRegistered()
  }, [destinationId])

  useEffect(() => {
    if(localStorage.getItem("auth")){
      let userType = localStorage.getItem("userType");
      let userId = localStorage.getItem("userId");
      setIsAuth(true);
      console.log(userType)
      if (userType==="admin") {
        console.log("admin")
        setIsAdmin(true);
      }
    }
  },[]);

  function getKeysFromMapArr(country) {
    let countryO = myMap.get(country)
    if(typeof countryO == "undefined"){
      setNotFound(true)
      return ''
    }
    else{
      setNotFound(false)
      return countryO
    }
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
          console.log(res.data)
          if (res.status === 200){
            if(res.data.error === ""){
            setData(res.data)
            setShowInfo(true)
            setLoader(false)}
            else{
              alert(res.data.error)
              setLoader(false)
            }
          }
          else{
            setFailed(true)
          }
        })
        setLoader(true)
      }
      catch (e){
        setFailed(true)
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
          setShowUnauthorized(true);
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
        <Logo authorized = {isAuth} admin={isAdmin}/>
        <WorldMap retrieveId = {retrieveId}/>
        {!showInfo?<SearchBar handleCountry = {handleCountry} handleKeyPress = {handleKeyPress} notfound = {notFound}/>:null}
        {showInfo?<CountryInfo country = {destinationName} data = {data}/>:null}
        <div style = {{position:"absolute", left:"900px", top: "450px"}}>
        <Spinner radius={150} color={"#F9B300"} stroke={10} visible={loader?true:false}/>
        </div>
        {failed?<NoData/>:null}
        {showUnauthorized?<UnauthorizedUserNotification/>:null}
      </div>
  )
}

export default MainPage