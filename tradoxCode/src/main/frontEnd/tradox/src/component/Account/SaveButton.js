import React from 'react';
import style from "./Account.module.css";

function SaveButton(props) {
    function changeState() {

    }

    return (
        <button type={props.type} className={style.createButton}>
            Save
        </button>
    )
}

export default SaveButton;