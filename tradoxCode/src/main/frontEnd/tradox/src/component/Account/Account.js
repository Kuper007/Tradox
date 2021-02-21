import React, {useEffect, useState} from 'react'
import style from './Account.module.css';
import logo from "../../images/LogoTradoxLogo.svg";
import user from "../../images/user.svg";
import InputForm from "../RegisterForm/InputForm";
import myMap from "../CountrysMap";
import SaveButton from "./SaveButton";
import {NavLink} from "react-router-dom";

function Account(props) {
    let [state, setState] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/api/v1/account/getUserData")
            .then(data => data.json())
            .then(userData => {
                if (!state) {
                    console.log(userData)
                    const {
                        firstName,
                        lastName,
                        birthDate,
                        email,
                        phone,
                        passport = JSON.stringify(passport.passportId),
                        citizenship = passport.citizenshipCountry.shortName,
                        currentCountry = location.fullName
                    } = userData;
                    let mmap = new Map().set("firstName", firstName).set("lastName", lastName)
                        .set("dateOfBirth", birthDate).set("email", email).set("mobilePhone", phone)
                        .set("passport", passport).set("citizenship", citizenship)
                        .set("currentCountry", currentCountry);
                    setState(mmap);
                }
            });
    })

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
                <NavLink to = '/'> <img src={logo}/></NavLink>
            </div>
            <div className={style.accountComponent}>
                <div className={style.user}>
                    <img src={user}/>
                    <div className={style.userInfo}>
                        <div className={style.title}>
                            Personal Info
                        </div>
                        <div className={style.info}>
                            <div>{state ? (
                                <form className={style.form} onSubmit={onClickSave}>
                                    <div>{console.log(state)}
                                        <InputForm value={state.get("firstName")} type={"text"}
                                                   title={"First name"} placeholder={state.get("firstName")}/>
                                        <InputForm value={state.get("lastName")} type={"text"}
                                                   title={"Last name"} placeholder={state.get("lastName")}/>
                                        <InputForm value={state.get("dateOfBirth")}
                                                   placeholder={state.get("dateOfBirth")}
                                                   title={"Date of birth"} type={"text"}/>
                                        <InputForm value={state.get("email")} type={"email"}
                                                   title={"E-mail"} placeholder={state.get("email")}/>
                                    </div>
                                    <div>
                                        <InputForm value={state.get("mobilePhone")} notFound={false}
                                                   keyOf={"mobilePhone"} placeholder={state.get("mobilePhone")}
                                                   type={"tel"} title={"Mobile phone"}/>
                                        <InputForm value={state.get("passport")} keyOf={"passport"} type={"text"}
                                                   title={"Passport"} placeholder={state.get("passport")}/>
                                        <InputForm value={state.get("citizenship")} notFound={false}
                                                   keyOf={"citizenship"} placeholder={state.get("citizenship")}
                                                   type={"text"} title={"Citizenship"}/>
                                        <InputForm value={state.get("currentCountry")} notFound={false}
                                                   keyOf={"currentCountry"} type={"text"} title={"Current country"}
                                                   placeholder={state.get("currentCountry")}/>
                                    </div>
                                </form>
                            ) : null}
                            </div>
                            <SaveButton type={"submit"}/>
                        </div>
                        <div className={style.line}></div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Account;

