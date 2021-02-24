import React, { useState,  useEffect }  from 'react'
import style from './FillDocs.module.css';
import logo from '../../images/LogoTradoxLogo.svg';

const FillDocs = () => {

    const [haveDocument, setHaveDocument] = useState(false);

    useEffect(() => {
        let departure = localStorage.getItem("departure");
        let destination = localStorage.getItem("destination");
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ "departure": departure,
                                   "destination": destination
                                 })
        };
        fetch("http://localhost:8080/api/v1/docs/fill", requestOptions).then(response => response.json().then(data => ({
            data: data,
            status: response.status })
        ).then(res => {
            if (res.data.res==="true"){
                //TODO: GET LOCAL FILE
            } else {
                console.log('error');
            }
        }));
    },[]);

    const getPdf = () => {
        fetch("http://localhost:8080/api/v1/docs/pdf").then(response => response.json().then(data => ({
            data: data,
            status: response.status })
        ).then(res => {
            if (res.data.res==="true"){
                //TODO: GET LOCAL FILE
            } else {
                console.log('error');
            }
        }));
    };

    const goBack = () => {

    };

    return (
        <div className={style.fillForm}>
            <div className={style.logo}>
                <img src={logo}/>
            </div>
                {haveDocument
                    ? (
                      <div className={style.container}>
                        <div className = {style.box}>
                              <h1>You don’t need special docs</h1>
                              <br></br>
                              <h1>Please visit embassy <br></br> at your country</h1>
                        </div>
                        <button onClick={() => goBack()} className = {style.btn}>Go back</button>
                      </div>
                    )
                    : (
                    <div className={style.container}>
                        <img src={logo} className={style.doc} />
                        <button onClick={() => getPdf()} className = {style.btn}>Get PDF</button>
                    </div>)}
        </div>
      )
}

export default FillDocs