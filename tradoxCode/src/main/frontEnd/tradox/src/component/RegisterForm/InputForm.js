import style from "./Register.module.css";
import React, {useState}  from 'react'
import Picker from "./CountryPicker";

function InputForm(props) {

    let [state,setState] = useState('');

    function changeState(e) {
        e.preventDefault()
        setState(e.target.value)
        props.changeState(props.keyOf,e.target.value)
    }

    return (
        <div className={style.oneInput}>
            <span className={style.naming}>
                {props.title}
            </span>
            {props.type === "countryPicker"
                ? <Picker keyOf={"citizenship"} changeState={changeState} array={props.array}/>
                : <input type={props.type} value={state} className={style.input} placeholder={props.placeholder} onChange={changeState} required/>}
        </div>
    )
}

export default InputForm