import React, {useState, useEffect} from 'react';
import style from './Logo.module.css'
import logo from '../../../images/LogoTradoxLogo.svg';
import user from '../../../images/user.svg';
import vector from '../../../images/Vector.svg';
import {NavLink} from 'react-router-dom';
import axios from "axios";

function Logo(props) {
   const[pressed, setPressed] = useState(false);

   function showAuth() {
        if (pressed === false)
        setPressed(true)
        else setPressed(false)
    }

   const logout = () => {
       try{
           axios.get("http://localhost:8080/api/v1/account/logout").then(res => {
               console.log(res.data)
               if (res.status === 200){
                   localStorage.removeItem("auth");
                   localStorage.removeItem("userId");
                   localStorage.removeItem("userType");
                   window.location.reload();
               }
               else{
               }
           })
       }

       catch (e) {
           console.log(`😱 Axios request failed: ${e}`);
       }
   };

    function refreshPage(){
        if (window.location.href = "/") window.location.reload();
    }
    return (
        <div className = {style.container}>
            <NavLink to = '/'><img src={logo} alt="logo" onClick={refreshPage} className = {style.logo}/></NavLink>
            <div className = {style.auth}>
            {pressed ?<div className = {style.hidable} >
                {!props.authorized? <div style= {{marginTop: '77px'}}>
                        <NavLink className = {`${style.register} ${style.link}`} to= '/registration'>Register</NavLink>
                    <NavLink className = {`${style.logIn} ${style.link}`} to = '/auth'>Log in</NavLink></div>:
                   <div style= {{marginTop: '77px'}}> <NavLink className = {`${style.register} ${style.link}`} to= '/account'>To account</NavLink>
                       {props.admin?<NavLink className = {`${style.logIn} ${style.link}`} to = '/admin'>Admin</NavLink>:null}
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
