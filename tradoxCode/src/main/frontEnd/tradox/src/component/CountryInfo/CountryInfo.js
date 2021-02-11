import React, {useState, useEffect} from 'react'
import CovidInfo from './CovidInfo/CovidInfo'
import styles from './CountryInfo.module.css'
import Documents from './Documents/Documents'
import Consulates from './Consulates/Consulates'
import News from './News/News'
import {NavLink} from 'react-router-dom'
 function CountryInfo(props) {
    const [opened, setOpened] = useState(false)
    const [departure, setDeparture] = useState([])
     const [covidInfo, setCovidInfo] = useState([])
     useEffect(() => {
         console.log(props);
         if(props.data.fullRoute.departure != undefined && props.data.covidInfo){
             setDeparture(props.data.fullRoute.departure)
             setCovidInfo(props.data.covidInfo)
             if(props.data.status == undefined || props.data.status == true){
                setOpened(true)
             }

         console.log(props.data.fullRoute.departure)}
     }, [departure])

    return (
        <div className = {styles.container}>
            {opened ? <div className = {styles.openCountry}>
                <h1 className = {styles.countryText}>{props.data.fullRoute.departure.fullName} is open for you</h1>
            </div>  :
            <div className = {styles.closedCountry}>
                <h1 className = {styles.countryText}>{props.data.fullRoute.departure.fullName} is closed for you</h1>
            </div>}
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