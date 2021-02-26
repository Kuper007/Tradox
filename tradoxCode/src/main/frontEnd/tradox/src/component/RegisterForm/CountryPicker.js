import React from 'react';
import style from './Register.module.css';
import {useState} from "react/cjs/react.production.min";

function Option(props) {
    return (
        <option>
            {props.title}
        </option>
    )
}

function Picker(props) {

    return (
        <select value={props.value} onChange={props.changeState} >
            {props.array.map((x,y) => <Option title={x} key={y}/>)}
        </select>
    )
}

export default Picker;
