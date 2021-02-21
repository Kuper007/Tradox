import React from 'react'
import style from './Documents.module.css'


export default function Documents(props) {
    const consulateInfo = Object.keys(props.data.object.documents.list)
        .map(item => <span key={props.data.object.documents.list[item].documentId}>
            {props.data.object.documents.list[item].description}<br/>
            {props.data.object.documents.list[item].name}
        </span>)
    const medicineInfo = Object.keys(props.data.object.medicines.list)
        .map(item => <span key={props.data.object.medicines.list[item].medicineId}>
            {props.data.object.medicines.list[item].name}
        </span>)
    return (
        <div className= {style.container}>
            <h2 className={style.head}>Documents</h2>
            {consulateInfo}
            <h2 className={style.head}>Medicine</h2>
            {medicineInfo}
        </div>
    )
}
