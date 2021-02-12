import React, {useEffect, useState} from 'react'
import style from './Account.module.css';
import logo from "../../images/LogoTradoxLogo.svg";
import user from "../../images/user.svg";
import InputForm from "../RegisterForm/InputForm";
import {NavLink} from "react-router-dom";
import myMap from "../CountrysMap";
import SaveButton from "./SaveButton";

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
                        passport,
                        citizenship,
                        location: currentCountry
                    } = userData;
                    setMainInfo({firstName, lastName, birthDate, email});
                    setAddInfo({phone, passport, citizenship, currentCountry});
                }
            });
    })

    //passport.passportId, passport.citizenshipCountry, location.fullName

    function onClickSave(e) {
        e.preventDefault();
        fetch('http://localhost:8080/api/v1/account/saveUserData', {
            method: "POST", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
                "first_name": state.get("firstName"),
                "last_name": state.get("lastName"),
                "birth_date": state.get("dateOfBirth"),
                "email": state.get("email"),
                "phone": state.get("mobilePhone"),
                "passport_id": state.get("passport"),
                "citizenship": myMap.get(state.get("citizenship")),
                "country_id": myMap.get(state.get("currentCountry"))
            })
        })
            .then(response => response.json()
                .then(data => ({
                        data: data,
                        status: response.status
                    })
                ));
    }

    return (
        <div>
            <div className={style.logo}>
                <img src={logo}/>
            </div>
            <div className={style.accountComponent}>
                <div className={style.user}>
                    <img src={user}/>
                    <div className={style.userInfo}>
                        <div className={style.title}>
                            Personal Info
                        </div>
                        <div className={style.info}>
                            <div>{mainInfo && addInfo ? (
                                <form className={style.form} onSubmit={onClickSave}>
                                    <div>{console.log(mainInfo)}
                                        <InputForm value={state.get("firstName")} notFound={false} type={"text"}
                                                   title={"First name"} keyOf={"firstName"}
                                                   placeholder={mainInfo.firstName}/>
                                        <InputForm value={state.get("lastName")} notFound={false} type={"text"}
                                                   title={"Last name"} keyOf={"lastName"}
                                                   placeholder={mainInfo.lastName}/>
                                        <InputForm value={state.get("dateOfBirth")} keyOf={"dateOfBirth"}
                                                   title={"Date of birth"} type={"text"}
                                                   placeholder={mainInfo.birthDate}/>
                                        <InputForm value={state.get("email")} keyOf={"email"} type={"email"}
                                                   title={"E-mail"} placeholder={mainInfo.email}/>
                                    </div>
                                    <div>{console.log(addInfo)}
                                        <InputForm value={state.get("mobilePhone")} notFound={false}
                                                   keyOf={"mobilePhone"}
                                                   type={"tel"} title={"Mobile phone"} placeholder={addInfo.phone}/>
                                        <InputForm value={state.get("passport")} keyOf={"passport"} type={"text"}
                                                   title={"Passport"} placeholder={addInfo.passport}/>
                                        <InputForm value={state.get("citizenship")} notFound={false}
                                                   keyOf={"citizenship"}
                                                   type={"text"} title={"Citizenship"}
                                                   placeholder={addInfo.citizenship}/>
                                        <InputForm value={state.get("currentCountry")} notFound={false}
                                                   keyOf={"currentCountry"} type={"text"} title={"Current country"}
                                                   placeholder={addInfo.currentCountry}/>
                                    </div>
                                </form>
                            ) : null}
                            </div>
                            <SaveButton type={"submit"}/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Account;

