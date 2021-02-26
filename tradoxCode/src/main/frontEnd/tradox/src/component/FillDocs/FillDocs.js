import React, { useState,  useEffect }  from 'react'
import style from './FillDocs.module.css';
import logo from '../../images/LogoTradoxLogo.svg';
import Spinner from 'react-spinner-material';

const FillDocs = () => {
    const [loader, setLoader] = useState(false);
    const [haveDocument, setHaveDocument] = useState(false);
    const [notHaveDocument, setNotHaveDocument] = useState(false)
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
                setLoader(false)
            } else {
                setNotHaveDocument( true)
                setLoader(false)
                console.log('error');
            }
        }));
        setLoader(true)
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
                {notHaveDocument
                    ?
                      <div className={style.container}>
                        <div className = {style.box}>
                              <h1>You don’t need special docs</h1>
                              <br></br>
                              <h1>Please visit embassy <br></br> at your country</h1>
                              <button onClick={() => goBack()} className = {style.btn}>Go back</button>
                        </div>
                      </div>:null}
            {haveDocument?<div className={style.container}>
                        <div className={style.imageBox}>
                            <img src={doc} className={style.doc} />
                            <a download="Insurance" href={pdf} className={style.btn}>Get PDF</a>
                        </div>
                    </div>:null}
            <div style = {{position:"absolute", left:"900px", top: "450px"}}>
                <Spinner radius={150} color={"#F9B300"} stroke={10} visible={loader?true:false}/>
            </div>
        </div>
      )
}

export default FillDocs