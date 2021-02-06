import React from 'react'
import style from './CovidInfo.module.css'
export default function CovidInfo(props) {

    return (
        <div className = {style.container}>
           <h1 style={{size:'18px'}}>Covid-19</h1>
            <span> All time</span> <span>Today</span>
            <h3>Total cases</h3>
            <span>{props.covidInfo.summaryTotalCases}</span> <span>{props.covidInfo.todayTotalCases}</span>
            <h3>Active cases</h3>
           <p></p> <span>{props.covidInfo.summaryActiveCases}</span> <span>{props.covidInfo.todayActiveCases}</span>
            <h3>Deaths</h3>
            <span>1232</span> <span>{props.covidInfo.todayDeaths}</span>
            <h3>Recovered</h3>
            <span>{props.covidInfo.summaryRecovered}</span> <span>{props.covidInfo.todayRecovered}</span>
        </div>
    )
}
