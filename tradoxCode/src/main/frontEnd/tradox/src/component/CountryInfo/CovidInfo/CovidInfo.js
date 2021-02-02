import React from 'react'
import style from './CovidInfo.module.css'
export default function CovidInfo(props) {
    return (
        <div className = {style.container}>
           <h1>Covid-19</h1>
            <span> All time Today</span>
            <h3>Total cases</h3>
            <span>{props.total} {props.totalToday}</span>
            <h3>Active cases</h3>
            <span>{props.activeTotal} {props.activeToday}</span>
            <h3>Deaths</h3>
            <span>{props.totalDeaths} {props.deathsToday}</span>
            <h3>Recovered</h3>
            <span>{props.recoveredTotal} {props.recoveredToday}</span>
        </div>
    )
}
