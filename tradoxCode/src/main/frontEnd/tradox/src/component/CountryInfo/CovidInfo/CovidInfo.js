import React from 'react'
import style from './CovidInfo.module.css'
export default function CovidInfo(props) {

    console.log(props);

    return (
        <div className = {style.container}>
           <h1 className={style.header}>Covid-19</h1>
            <div><span>All time</span> <span>Today</span></div>
            <h3>Total cases</h3>
            <div><span>{props.covidInfo.summaryTotalCases}</span> <span style={{color :"#B70202"}}>{props.covidInfo.todayTotalCases}</span></div>
            <h3>Active cases</h3>
            <div><span>{props.covidInfo.summaryActiveCases}</span><span style={{color :"#148534"}}>{props.covidInfo.todayActiveCases}</span></div>
            <h3>Deaths</h3>
            <div ><span>{props.covidInfo.summaryTotalDeaths}</span> <span style={{color :"#B70202"}}>{props.covidInfo.todayDeaths}</span></div>
            <h3>Recovered</h3>
            <div><span>{props.covidInfo.summaryRecovered}</span> <span style={{color :"#148534"}}>{props.covidInfo.todayRecovered}</span></div>
        </div>
    )
}
