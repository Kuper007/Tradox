import React from 'react'
import style from './Account.module.css';
import image from './savedRouteImage.svg';
import deleteButton from './deleteButton.svg';

function SavedRoute(props) {

    function onClickS(e) {
        let id = e.target.getAttribute("data-index");
        props.deleteCard(id);
        fetch('http://localhost:8080/api/v1/account/deleteRoute', {
            method: "POST", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
                "routeId": id
            })
        })
    }

    function onClickG(e) {
        let id = e.target.getAttribute("data-index");
        fetch('http://localhost:8080/api/v1/account/getSavedRoute', {
            method: "POST", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
                "routeId": id
            })
        })
    }

    return (
        <div className={style.savedRoute}>
            <div className={style.savedRouteTitle}>
                {props.title}
            </div>
            <img data-index={props.id} onClick={onClickS} className={style.deleteButton} src={deleteButton}/>
            <div className={style.countrysShortName}>
                <div className={style.marginNone}>{props.startCountry}</div>
                <div className={style.marginNone}>{props.endCountry}</div>
            </div>
            <img src={image} className={style.savedRouteImage}/>
            <button data-index={props.id} onClick={onClickG} className={style.savedRouteButton}>
                Go to route
            </button>
        </div>
    )
}

export default SavedRoute