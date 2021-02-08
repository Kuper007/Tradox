import React, {useState, useEffect}  from 'react'
import style from './PasswordReset.module.css';
import logo from '../../images/LogoTradoxLogo.svg';
import {NavLink,Redirect} from "react-router-dom";

function PasswordReset(){

    const[email,setEmail] = useState("");
    const[errorVisible,setErrorVisible] = useState(false);
    const[redirect,setRedirect] = useState(false);

    const send = () => {
          const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({'email': email})
          };
          fetch("http://localhost:8080/api/v1/reset/mail", requestOptions).then(response => response.json().then(data => ({
            data: data,
            status: response.status })
          ).then(res => {
            if (res.data.res=="true"){
                setRedirect(true);
            } else {
                console.log("Connection error");
            }
          }));

    };

    return (
        <div className={style.container}>
            <NavLink to = '/'><img src={logo} alt="logo"/></NavLink>
            <h2>Verification</h2>
            <form>
                <label>
                    We will send you your new password to this email:
                    <input type="email" value={email} onChange={event => setEmail(event.target.value)} />
                </label>
            </form>
            {errorVisible ?(<span>Invalid email</span>) :null}
            <button onClick={() => send()} className = {style.sendBtn}> Send password</button>
            {redirect ? <Redirect to='/auth'/>  :null}
        </div>
    );

}

export default PasswordReset;