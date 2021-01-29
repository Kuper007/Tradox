import React from 'react';
import style from "./Register.module.css";

function CreateButton(props) {
    return (
        <button type={props.type} className={style.createButton}>
            Create account
        </button>
    )
}

export default CreateButton