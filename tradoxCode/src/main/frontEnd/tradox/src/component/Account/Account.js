import React, {useEffect, useState} from 'react'
import style from './Account.module.css';
import logo from "../../images/LogoTradoxLogo.svg";
import InputForm from "../RegisterForm/InputForm";
import {NavLink} from "react-router-dom";

function Account(props) {
    const [mainInfo, setMainInfo] = useState(null);
    const [addInfo, setAddInfo] = useState(null);
    let infoMap = new Map().set("firstName", null).set("lastName", null).set("dateOfBirth", null).set("email", null)
        .set("password", null).set("mobilePhone", null).set("passport", null).set("citizenship", null).set("currentCountry", null);

    let [state, setState] = useState(infoMap);

    useEffect(() => {
        fetch("http://localhost:8080/api/v1/account/getUserData")
            .then(data => data.json())
            .then(userData => {
                if (!mainInfo && !addInfo) {
                    const {
                        firstName,
                        lastName,
                        birthDate,
                        email,
                        phone,
                        passportId,
                        citizenshipCountry,
                        location: currentCountry
                    } = userData;
                    setMainInfo({firstName, lastName, birthDate, email});
                    setAddInfo({phone, passportId, citizenshipCountry, currentCountry});
                    console.log(addInfo);
                }
            });
    })


    return (
        <div className={style.accountComponent}>
            <div>pppppppppppp</div>
            <div className={style.accountLogo}>
                <NavLink to='/'> <img src={logo}/></NavLink>
                <div className={style.userInfo}>
                    <div className={style.title}>
                        Personal Info
                    </div>
                    <div className={style.info}>
                        <div>{mainInfo && addInfo ? (
                            <form className={style.form}>
                                <div>{console.log(mainInfo)}
                                    <InputForm value={state.get("firstName")} notFound={false} type={"text"}
                                               title={"First name"} keyOf={"firstName"}
                                               placeholder={mainInfo.firstName}/>
                                    <InputForm value={state.get("lastName")} notFound={false} type={"text"}
                                               title={"Last name"} keyOf={"lastName"}
                                               placeholder={mainInfo.lastName}/>
                                    <InputForm value={state.get("dateOfBirth")} keyOf={"dateOfBirth"}
                                               title={"Date of birth"} type={"date"} placeholder={mainInfo.birthDate}/>
                                    <InputForm value={state.get("email")} keyOf={"email"} type={"email"}
                                               title={"E-mail"} placeholder={mainInfo.email}/>
                                </div>
                                <div>
                                    <InputForm value={state.get("mobilePhone")} notFound={false} keyOf={"mobilePhone"}
                                               type={"tel"} title={"Mobile phone"} placeholder={addInfo.phone}/>
                                    <InputForm value={state.get("passport")} keyOf={"passport"} type={"text"}
                                               title={"Passport"} placeholder={addInfo.passportId}/>
                                    <InputForm value={state.get("citizenship")} notFound={false} keyOf={"citizenship"}
                                               type={"text"} title={"Citizenship"}
                                               placeholder={addInfo.citizenshipCountry}/>
                                    <InputForm value={state.get("currentCountry")} notFound={false}
                                               keyOf={"currentCountry"} type={"text"} title={"Current country"}
                                               placeholder={addInfo.currentCountry}/>
                                </div>
                            </form>
                        ) : null}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Account;

