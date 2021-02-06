import React, {useState, useEffect} from 'react'
import CovidInfo from './CovidInfo/CovidInfo'
import styles from './CountryInfo.module.css'
import Documents from './Documents/Documents'
import Consulates from './Consulates/Consulates'
import News from './News/News'
import {NavLink} from 'react-router-dom'
 function CountryInfo(props) {
    const [departure, setDeparture] = useState([])
     const [covidInfo, setCovidInfo] = useState([])
     useEffect(() => {
         if(props.data.departure != undefined && props.data.destination.covidInfo){
             setDeparture(props.data.departure)
             setCovidInfo(props.data.destination.covidInfo)
         console.log(props.data.departure)}
     })

    return (
        <div className = {styles.container}> 
            <div className = {styles.countryStatus}>
                <h1 className = {styles.countryText}>{props.country} is open for you</h1>
            </div>
            <CovidInfo covidInfo = {covidInfo}/>
            <Documents/>
            <NavLink to = '/docs'><div className={styles.fillBtn}>
                <h2 className={styles.fillDocsTxT}>Fill out documents</h2>
            </div></NavLink>
            <Consulates departure = {departure}/>
            <News/>
        </div>
    )
}
export default CountryInfo;