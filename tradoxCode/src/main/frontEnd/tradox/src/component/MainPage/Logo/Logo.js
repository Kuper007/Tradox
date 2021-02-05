import React, {useState, useEffect} from 'react';
import style from './Logo.module.css'
import logo from '../../../images/LogoTradoxLogo.svg';
import user from '../../../images/user.svg';
import vector from '../../../images/Vector.svg';
import {NavLink} from 'react-router-dom';
import axios from "axios";

function Logo(props) {
   const[pressed, setPressed] = useState();
    useEffect(() => {
        setPressed(true)
    }, [pressed])

    function testLogIn(){
        axios.get("http://localhost:8080/api/v1/route/test").then(res =>{
                console.log(res.status);
            });
    }

   function showAuth() {
        if (pressed === false)
        setPressed(true)
        else setPressed(false)
    }

    return (
        <div className = {style.container}>
            <NavLink to = '/'><img src={logo} alt="logo" className = {style.logo}/></NavLink>
            <div className = {style.auth}>
            {pressed ?<div className = {style.hidable} >
                {!props.authorized? <div style= {{marginTop: '77px'}}>
                    <button onClick={testLogIn}>test log in</button>
                        <NavLink className = {`${style.register} ${style.link}`} to= '/registration'>Register</NavLink>
                    <NavLink className = {`${style.logIn} ${style.link}`} to = '/auth'>Log in</NavLink></div>:
                   <div style= {{marginTop: '77px'}}> <NavLink className = {`${style.register} ${style.link}`} to= '/account'>To account</NavLink>
                    <NavLink className = {`${style.logOut} ${style.link}`} to = '/logout'>Log out</NavLink></div>
                }
                    <img src = {vector} alt = 'vector' className = {style.vector}/>
                </div>: null}
                <img src={user}  alt="user" className = {style.user} onMouseDown={showAuth}/>
            </div>
        </div>
    )
}
    



export default Logo
