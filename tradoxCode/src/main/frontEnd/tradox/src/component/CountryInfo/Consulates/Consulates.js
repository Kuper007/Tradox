import React from 'react'
import style from './Consulates.module.css'
export default function Consulates(props) {
    const consulateInfo = Object.keys(props.data.consulates.list)
        .map(item => <span className={style.consulateItem} key={props.data.consulates.list[item].consulateId}>
            {props.data.consulates.list[item].city}, {props.data.consulates.list[item].address}<br/>
            {props.data.consulates.list[item].phoneNumber}<br/>
        </span>)
    return (
        <div className = {style.container}>
            <h2 className={style.head}>Consulates of {props.departure.fullName}</h2>
            <div className={style.consulatesBox}>
            {consulateInfo}
            </div>
            <h2 className={style.exchangeTitle}>Exchange rates</h2>
        </div>
    )
}
