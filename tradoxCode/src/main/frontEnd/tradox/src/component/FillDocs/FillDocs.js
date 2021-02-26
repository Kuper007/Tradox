import React, { useState,  useEffect }  from 'react'
import style from './FillDocs.module.css';
import logo from '../../images/LogoTradoxLogo.svg';
import world from '../../images/world-21.svg'

const FillDocs = () => {

    const [haveDocument, setHaveDocument] = useState(false);
    const [doc, setDoc] = useState("");
    const [pdf, setPdf] = useState("");

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
                setDoc(res.data.img);
                setPdf(res.data.pdf);
                setHaveDocument(true);
            } else {
                console.log('error');
            }
        }));
    },[]);

    const goBack = () => {
        localStorage.removeItem("departure");
        localStorage.removeItem("destination");
        window.location.href = "http://localhost:8080/";
    };

    return (
        <div className={style.fillForm} style={{backgroundImage: `url($(world))`}}>
            <div className={style.logo}>
                <img src={logo} onClick={()=>goBack()}/>
            </div>
                {!haveDocument
                    ? (
                      <div className={style.container}>
                        <div className = {style.box}>
                              <h1>You don’t need special docs</h1>
                              <br></br>
                              <h1>Please visit embassy <br></br> at your country</h1>
                              <button onClick={() => goBack()} className = {style.btn}>Go back</button>
                        </div>
                      </div>
                    )
                    : (
                    <div className={style.container}>
                        <div className={style.imageBox}>
                            <img src={doc} className={style.doc} />
                            <a download="Insurance" href={pdf} className={style.btn}>Get PDF</a>
                        </div>
                    </div>)}
        </div>
      )
}

export default FillDocs