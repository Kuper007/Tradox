import React from 'react'
import style from './Consulates.module.css'
export default function Consulates(props) {
    const consulateInfo = Object.keys(props.data.object.consulates.list)
        .map(item => <span className={style.consulateItem} key={props.data.object.consulates.list[item].consulateId}>
            {props.data.object.consulates.list[item].city}, {props.data.object.consulates.list[item].address}<br/>
            {props.data.object.consulates.list[item].phoneNumber}<br/>
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
