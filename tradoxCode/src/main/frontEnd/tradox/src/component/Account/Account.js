import React, {useEffect, useState} from 'react'
import style from './Account.module.css';
import logo from "../../images/LogoTradoxLogo.svg";
import InputForm from "../RegisterForm/InputForm";
import CreateButton from "../RegisterForm/CreateButton";

function Account(props) {
    const [user, setUser] = useState(null);

    useEffect(() => {
        fetch("")
            .then(data => data.json())
            .then(/*TODO: преобразовать полученный объект*/)
            .then(userData => setUser(userData));
    })

    const {
        firstName,
        lastName,
        dateOfBirth,
        email,
        password,
        mobilePhone,
        passport,
        citizenship,
        currentCountry
    } = user;

    return (
        <div className={style.accountComponent}>
            <div className={style.accountLogo}>
                <NavLink to='/'> <img src={logo}/></NavLink>
                <div className={style.userInfo}>
                    <div className={style.title}>
                        Personal Info
                    </div>
                    <div className={style.info}>
                        <div>
                            <form className={style.form} onSubmit={onClickS}>
                                <InputForm value={state.get("firstName")} notFound={false} type={"text"}
                                           title={"First name"} keyOf={"firstName"} placeholder={firstName}
                                           changeState={changeInfoMap}/>
                                <InputForm value={state.get("lastName")} notFound={false} type={"text"}
                                           title={"Last name"} changeState={changeInfoMap} keyOf={"lastName"}
                                           placeholder={"Savchak"}/>
                                <InputForm value={state.get("dateOfBirth")} keyOf={"dateOfBirth"}
                                           title={"Date of birth"} changeState={changeInfoMap} type={"date"}/>
                                <InputForm value={state.get("email")} notFound={notFoundMail} keyOf={"email"}
                                           type={"email"} title={"E-mail"} changeState={changeInfoMap}
                                           placeholder={"example@gmail.com"}/>
                                <InputForm value={state.get("password")} notFound={false} keyOf={"password"}
                                           type={"password"} changeState={changeInfoMap} placeholder={"*******"}
                                           title={"Password"}/>
                                <InputForm value={state.get("mobilePhone")} notFound={false} keyOf={"mobilePhone"}
                                           type={"tel"} changeState={changeInfoMap} title={"Mobile phone"}
                                           placeholder={"+39094234433"}/>
                                <InputForm value={state.get("passport")} notFound={notFoundPassport}
                                           keyOf={"passport"} type={"text"} title={"Passport"}
                                           changeState={changeInfoMap} placeholder={"MK212133"}/>
                                <InputForm value={state.get("citizenship")} notFound={false} keyOf={"citizenship"}
                                           type={"countryPicker"} changeState={changeInfoMap} title={"Citizenship"}
                                           placeholder={"Ukraine"} array={getKeysFromMapArr()}/>
                                <InputForm value={state.get("currentCountry")} notFound={false}
                                           keyOf={"currentCountry"} type={"countryPicker"}
                                           changeState={changeInfoMap} title={"Current country"}
                                           placeholder={"Ukraine"} array={getKeysFromMapArr()}/>
                                <CreateButton type={"submit"}/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Account;

