import React, { Component } from 'react'
import style from './UnregisteredUserNotification.module.css';
export class UnregisteredUserNotification extends Component {
    render() {
        return (
            <div className = {style.container}>
                <div className = {style.wrapper}>
                 <h1 className = {style.msg}>Sorry, but you are not <br/>registered</h1>
                 <input className = {style.register} type ="button" value= "Register"/>
                </div> 
            </div>
        )
    }
}

export default UnregisteredUserNotification