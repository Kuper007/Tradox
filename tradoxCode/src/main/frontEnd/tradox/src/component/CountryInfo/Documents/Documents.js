import React from 'react'
import style from './Documents.module.css'


export default function Documents(props) {
    return (
        <div className= {style.container}>
            <h2>Documents</h2>
            {props.docs}
            <h2>Medicine</h2>
            {props.med}
        </div>
    )
}
