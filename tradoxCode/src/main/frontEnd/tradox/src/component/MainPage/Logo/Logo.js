import React, {useState, useEffect} from 'react';
import style from './Logo.module.css'
import logo from '../../../images/LogoTradoxLogo.svg';
import user from '../../../images/user.svg';
import vector from '../../../images/Vector.svg';
import {NavLink} from 'react-router-dom';

function Logo(props) {
   const[pressed, setPressed] = useState(false);

   function showAuth() {
        if (pressed === false)
        setPressed(true)
        else setPressed(false)
    }

   const logout = () => {
        localStorage.removeItem("auth");
        localStorage.removeItem("userId");
        window.location.reload();
   };

    return (
        <div className = {style.container}>
            <NavLink to = '/'><img src={logo} alt="logo" className = {style.logo}/></NavLink>
            <div className = {style.auth}>
            {pressed ?<div className = {style.hidable} >
                {!props.authorized? <div style= {{marginTop: '77px'}}>
                        <NavLink className = {`${style.register} ${style.link}`} to = '/registration'>Register</NavLink>
                    <NavLink className = {`${style.logIn} ${style.link}`} to = '/auth'>Log in</NavLink></div>:
                   <div style= {{marginTop: '77px'}}>
                       <NavLink className = {`${style.register} ${style.link}`} to= '/account'>To account</NavLink>
                   <span className={style.logOut} onClick={() => logout()}>Log out</span></div>
                }
                    <img src = {vector} alt = 'vector' className = {style.vector}/>
                </div>: null}
                <img src={user}  alt="user" className = {style.user} onMouseDown={showAuth}/>
            </div>
        </div>
    )
}
    



export default Logo;
