import React, { Component } from 'react'
import style from './UnauthorizedUserNotification.module.css';
import {NavLink} from "react-router-dom";
export class UnauthorizedUserNotification extends Component {
    render() {
        return (
            <div className = {style.container}>
                <div className = {style.wrapper}>
                 <h1 className = {style.msg}>Sorry, but you are not <br/>authorized</h1>
                 <NavLink to='/registration'><input className = {style.register} type ="button" value= "Log In"/></NavLink>
                </div> 
            </div>
        )
    }
}

export default UnauthorizedUserNotification