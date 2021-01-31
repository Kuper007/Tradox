import React, {useState}  from 'react'
import style from './Auth.module.css';
import logo from '../../images/LogoTradoxLogo.svg';

function Auth(){

    const [emailVal, setEmailVal] = useState("");
    const [passwordVal, setPasswordVal] = useState("");

    const validate = () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: "'"+emailVal+"'", password: passwordVal })
        };
        fetch("http://localhost:8080/api/v1/auth/check", requestOptions).then((res)=>{
            console.log(res);
            console.log(res.json());
        });
    };

    return (
            <div className = {style.container}>
                <NavLink to = '/'><img src={logo} alt="logo"/></NavLink>
                <h2>Authorisation</h2>
                <form className = {style.authForm}>
                    <label>
                        Email:
                        <input value={emailVal} onChange={event => setEmailVal(event.target.value)} type="text" id="email" />
                    </label>
                    <label>
                        Password:
                        <input value={passwordVal} onChange={event => setPasswordVal(event.target.value)} type="text" id="password" />
                    </label>
                </form>
                <div className = {style.btnGroup}>
                    <button onClick={() => {}}> Forgot password? </button>
                    <br></br>
                    <button onClick={() => {}}> Register </button>
                </div>
                <button onClick={() => validate()} className = {style.authBtn}> Log in </button>
            </div>
        );
    }

export default Auth