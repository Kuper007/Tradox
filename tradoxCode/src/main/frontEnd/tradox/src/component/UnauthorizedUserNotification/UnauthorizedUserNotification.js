import React, { Component } from 'react'
import style from './UnauthorizedUserNotification.module.css';
export class UnauthorizedUserNotification extends Component {
    render() {
        return (
            <div className = {style.container}>
                <div className = {style.wrapper}>
                 <h1 className = {style.msg}>Sorry, but you are not <br/>authorized</h1>
                 <input className = {style.register} type ="button" value= "Log In"/>
                </div> 
            </div>
        )
    }
}

export default UnauthorizedUserNotification