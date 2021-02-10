import React, {useState}  from 'react'
import style from './Auth.module.css';
import logo from '../../images/LogoTradoxLogo.svg';
import {NavLink} from "react-router-dom";

function Auth(props){

    const [emailVal, setEmailVal] = useState("");
    const [passwordVal, setPasswordVal] = useState("");
    const validate = () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ "email": emailVal, "password": passwordVal })
        };
        fetch("http://localhost:8080/api/v1/auth/check", requestOptions).then(response => response.json().then(data => ({
            data: data,
            status: response.status })
        ).then(res => {
            if (res.data.res==="true"){
                let userId = res.data.userId;
                //setAuthorized(true);
                localStorage.setItem('userId', userId);
                localStorage.setItem('auth', true);
                localStorage.setItem("userType",res.data.userType);
                window.location.href = "http://localhost:8080/";
            } else {
                //TODO: SHOW ALERTS
                if (res.data.res==="email"){
                    console.log("There is no user with such email");
                }
                if (res.data.res==="password"){
                    console.log("Invalid password");
                }
                if (res.data.res==="network"){
                    console.log("Something is wrong with connection");
                }
            }
        }));
    };

    return (
            <div className={style.container}>
                <NavLink to = '/'><img src={logo} alt="logo"/></NavLink>
                <h2>Authorization</h2>
                <form>
                    <label>
                        <span>Email:</span>
                        <input value={emailVal} onChange={event => setEmailVal(event.target.value)} type="email" id="email" />
                    </label>
                    <label>
                        <span>Password</span>
                        <input value={passwordVal} onChange={event => setPasswordVal(event.target.value)} type="password" id="password" />
                    </label>
                </form>
                <div>
                    <NavLink to='/reset'>
                        <span> Forgot password? </span>
                    </NavLink>
                    <br/>
                    <NavLink to='/registration'>
                        <span> Register </span>
                    </NavLink>
                </div>
                <button className={style.authBtn} onClick={() => validate()}> Log in </button>
            </div>
        );
    }

export default Auth