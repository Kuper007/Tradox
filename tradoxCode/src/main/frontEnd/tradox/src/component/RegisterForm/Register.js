import React, {useState}  from 'react'
import style from './Register.module.css';
import logo from '../../images/LogoTradoxLogo.svg';
import InputForm from "./InputForm";
import CreateButton from "./CreateButton";
import Picker from "./CountryPicker";
import myMap from "../CountrysMap"
import {NavLink, Redirect} from "react-router-dom";

function Register(props) {

    function getKeysFromMapArr() {
        let arr = []
        for (let [key, value] of myMap) {
            arr.push(key)
        }
        return arr;
    }

    let [isTapped,tap] = useState(false);



    let infoMap = new Map().set("firstName",null).set("lastName",null).set("dateOfBirth",null).set("email",null)
        .set("password",null).set("mobilePhone",null).set("passport",null).set("citizenship",null).set("currentCountry",null);

    let [state, setState] = useState(infoMap);
    const[redirect,setRedirect] = useState(false);

    // function checkInfoMap() {
    //     const keys = infoMap.keys();
    //     for (let [key, value] of infoMap) {
    //         if (value === null) {
    //             alert(key + " should be written!!!");
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    function changeInfoMap(inputKey,inputValue) {
        infoMap.set(inputKey,inputValue)
    }

    let [notFoundMail,notFoundMailFunc] = useState(false)
    let [notFoundPassport,notFoundPassportFunc] = useState(false)

    function onClickS(e) {
        e.preventDefault();
        fetch('http://localhost:8080/api/v1/registration/fill/',{method:"POST", headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({
                "first_name": state.get("firstName"),
                "last_name": state.get("lastName"),
                "birth_date": state.get("dateOfBirth"),
                "email": state.get("email"),
                "password": state.get("password"),
                "phone": state.get("mobilePhone"),
                "passport_id": state.get("passport"),
                "citizenship": myMap.get(state.get("citizenship")),
                "country_id": myMap.get(state.get("currentCountry"))
        })}).then(response => response.json().then(data => ({
            data: data,
            status: response.status })
        ).then(res => {
             if (res.data.result === true) {
                //tap(true);
                localStorage.setItem("userId",res.data.userId);
                setRedirect(true);
                //window.location.href = "http://localhost:8080/";
             } else if (res.data.emailNotUnique === true) {
                notFoundMailFunc(true);
                alert("Account with this mail already exist. Please change mail!")
             } else if (res.data.passportNotUnique === true) {
                notFoundPassportFunc(true);
                alert("Account with this passport already exist. Please change passport!")
             }
        }));
    }

    return (
        <div className={style.registrationComponent}>
        {redirect? <Redirect to='/verification'/> : null}
        <div className={style.registerLogo}>
            <NavLink to = '/'> <img src={logo}/></NavLink>
        </div>
            {isTapped
                ? <div className={style.verifyTitle}>We send you letter on email. Please, verify to start using your account.</div>
                : (
                <div className={style.userInfo}>
                    <div className={style.title}>
                        Registration
                    </div>
                    <div className={style.info}>
                        <div>
                            <form className={style.form} onSubmit={onClickS}>
                                <InputForm value={state.get("firstName")} notFound={false} type={"text"} title={"First name"} keyOf={"firstName"} placeholder={"Danylo"} changeState={changeInfoMap} />
                                <InputForm value={state.get("lastName")} notFound={false} type={"text"} title={"Last name"} changeState={changeInfoMap} keyOf={"lastName"} placeholder={"Savchak"}/>
                                <InputForm value={state.get("dateOfBirth")} keyOf={"dateOfBirth"} title={"Date of birth"} changeState={changeInfoMap} type={"date"} />
                                <InputForm value={state.get("email")} notFound={notFoundMail} keyOf={"email"} type={"email"} title={"E-mail"} changeState={changeInfoMap} placeholder={"example@gmail.com"}/>
                                <InputForm value={state.get("password")}  notFound={false} keyOf={"password"} type={"password"} changeState={changeInfoMap} placeholder={"*******"} title={"Password"}/>
                                <InputForm value={state.get("mobilePhone")} notFound={false} keyOf={"mobilePhone"} type={"tel"} changeState={changeInfoMap} title={"Mobile phone"} placeholder={"+39094234433"}/>
                                <InputForm value={state.get("passport")}  notFound={notFoundPassport} keyOf={"passport"} type={"text"} title={"Passport"} changeState={changeInfoMap} placeholder={"MK212133"}/>
                                <InputForm value={state.get("citizenship")} notFound={false} keyOf={"citizenship"}  type={"countryPicker"} changeState={changeInfoMap} title={"Citizenship"} placeholder={"Ukraine"} array={getKeysFromMapArr()}/>
                                <InputForm value={state.get("currentCountry")}  notFound={false} keyOf={"currentCountry"}  type={"countryPicker"} changeState={changeInfoMap} title={"Current country"} placeholder={"Ukraine"}  array={getKeysFromMapArr()}/>
                                <CreateButton type={"submit"}/>
                            </form>
                        </div>
                    </div>
                </div>)}
        </div>
    )
}

export default Register;