import React from 'react'
import CovidInfo from './CovidInfo/CovidInfo'
import styles from './CountryInfo.module.css'
import Documents from './Documents/Documents'
import Consulates from './Consulates/Consulates'
import News from './News/News'
import {NavLink} from 'react-router-dom'
 function CountryInfo(props) {
    return (
        <div className = {styles.container}> 
            <div className = {styles.countryStatus}>
                <h1 className = {styles.countryText}>{props.country} is open for you</h1>
            </div>
            <CovidInfo/>
            <Documents/>
            <NavLink to = '/docs'><div className={styles.fillBtn}>
                <h2 className={styles.fillDocsTxT}>Fill out documents</h2>
            </div></NavLink>
            <Consulates country = {props.country}/>
            <News/>
        </div>
    )
}
export default CountryInfo;