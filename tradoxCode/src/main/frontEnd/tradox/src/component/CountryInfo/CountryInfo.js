import React from 'react'
import CovidInfo from './CovidInfo/CovidInfo'
import carType from '../../images/transportation.svg'
import styles from './CountryInfo.module.css'
import Documents from './Documents/Documents'
import Consulates from './Consulates/Consulates'
import News from './News/News'
 function CountryInfo(props) {
    return (
        <div className = {styles.container}> 
            <div className = {styles.countryStatus}>
                <h1 className = {styles.countryText}>{props.countryName} is open for you</h1>
            </div>
            <img src={carType} alt="car" className = {styles.car}/>
            <CovidInfo/>
            <Documents/>
            <Consulates country = {props.countryName}/>
            <News/>
        </div>
    )
}
export default CountryInfo;