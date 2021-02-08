import React, {useState, useEffect}  from 'react'
import style from './Verification.module.css';
import logo from '../../images/LogoTradoxLogo.svg';
import {NavLink} from "react-router-dom";

function Verification(){

    const[code,setCode] = useState("");
    const[realCode, setRealCode] = useState("");
    const[errorVisible, setErrorVisible] = useState(false);

    useEffect(() => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' }
        };
        fetch("http://localhost:8080/api/v1/verification/mail", requestOptions).then(response => response.json().then(data => ({
            data: data,
            status: response.status })
        ).then(res => {
            console.log(res.data);
            if(res.data.res==="true"){
                setRealCode(res.data.code);
            } else {
                console.log("Connection error");
            }
        }));
    },[])

     useEffect(() => {

      }, [errorVisible])

    const check = () => {
        console.log(code);
        console.log(realCode);
        if (code===realCode){
            const requestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            };
            fetch("http://localhost:8080/api/v1/verification/verify", requestOptions).then(response => response.json().then(data => ({
                data: data,
                status: response.status })
            ).then(res => {
                console.log(res.data);
                if(res.data.res==="true"){
                    window.location.href = "http://localhost:8080/";
                } else {
                    console.log("Connection error");
                }
            }));
        } else {
            setErrorVisible(true);
        }
    };

    return(
        <div className={style.container}>
            <NavLink to = '/'><img src={logo} alt="logo"/></NavLink>
            <h2>Verification</h2>
            <form>
              <label>
                Type the code that was sent to your e-mail: {}
                <input type="text" value={code} onChange={event => setCode(event.target.value)} />
              </label>
            </form>
            {errorVisible ?(<span>Invalid code</span>) :null}
            <button onClick={() => check()} className = {style.checkBtn}> Send </button>
        </div>
    );

}

export default Verification;