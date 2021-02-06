import React from 'react'
import style from './Consulates.module.css'
export default function Consulates(props) {
    return (
        <div className = {style.container}>
            <h2>Consulates of {props.departure.fullName}</h2>
        </div>
    )
}
