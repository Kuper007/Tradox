import React, {useEffect, useState} from 'react'
import style from './Account.module.css';
import logo from "../../images/LogoTradoxLogo.svg";
import user from "../../images/user.svg";
import InputForm from "../RegisterForm/InputForm";
import myMap from "../CountrysMap";
import {NavLink} from "react-router-dom";
import Scrollable from "./Scrollable";
import SavedRoute from "./SavedRoute";

function Account(props) {
    const [cards, setCards] = useState([]);
    let infoMap = new Map().set("firstName", "Danylo").set("lastName", "Savchak").set("dateOfBirth", "15.07.2000").set("email", "daniel.savchak@gmail.com")
        .set("password", "121322").set("mobilePhone", "+380952023455").set("passport", "PMEWEWE").set("citizenship", "Ukraine").set("currentCountry", "Ukraine");
    let [state, setState] = useState(infoMap);
    let [firstName,setFirstName] = useState("")
    let [useEffectAtStart,setUseEffectAtStart] = useState(false)

    useEffect(() => {
        if (!useEffectAtStart) {
        fetch("http://localhost:8080/api/v1/account/getUserData")
            .then(data => data.json())
            .then(userData => {
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
                    // let mmap = new Map().set("firstName", firstName).set("lastName", lastName)
                    //     .set("dateOfBirth", birthDate).set("email", email).set("mobilePhone", phone)
                    //     .set("passport", passport).set("citizenship", citizenship)
                    //     .set("currentCountry", currentCountry);
                    infoMap.set("firstName",firstName)
                    infoMap.set("lastName",lastName)
                    infoMap.set("dateOfBirth",birthDate)
                    infoMap.set("email",email)
                    infoMap.set("mobilePhone",phone)
                    infoMap.set("passport",passport)
                    infoMap.set("citizenship",citizenship)
                    infoMap.set("currentCountry",currentCountry)

                    setFirstName(firstName)
                    setState(infoMap)
                    // setState(mmap);
                    setUseEffectAtStart(true)

                    userData.transit.forEach((card) => {
                        console.log(card);
                        setCards([...cards, {
                            id: card.routeId,
                            from: card.transit[0].fullRoute.departure.shortName,
                            to: card.transit[0].fullRoute.destination.shortName,
                            title: card.transit[0].fullRoute.destination.fullName
                        }])
                    })

            });
        }
    })

    function onClickSave(e) {
        e.preventDefault();
        console.log(infoMap)
        fetch('http://localhost:8080/api/v1/account/saveUserData', {
            method: "POST", headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
                "first_name": infoMap.get("firstName"),
                "last_name": infoMap.get("lastName"),
                "birth_date": infoMap.get("dateOfBirth"),
                "email": infoMap.get("email"),
                "phone": infoMap.get("mobilePhone"),
                "passport_id": infoMap.get("passport"),
                "citizenship": myMap.get(infoMap.get("citizenship")),
                "country_id": myMap.get(infoMap.get("currentCountry"))
            })
        })
        .then(response => response.json()
            .then(data => ({
                    data: data,
                    status: response.status
                })
            ));
    }

    const items = [
        {
            title: 'Ukraine',
            startCountry: 'MD',
            endCountry: 'US'
        },
        {
            title: 'France',
            startCountry: 'MD',
            endCountry: 'US'
        }, {
            title: 'United States of America',
            startCountry: 'MD',
            endCountry: 'US'
        }, {
            title: 'England',
            startCountry: 'MD',
            endCountry: 'US'
        }, {
            title: 'Brazil',
            startCountry: 'MD',
            endCountry: 'US'
        }, {
            title: 'Moldova',
            startCountry: 'MD',
            endCountry: 'US'
        }, {
            title: 'Romania',
            startCountry: 'MD',
            endCountry: 'US'
        }, {
            title: 'Kukurudza',
            startCountry: 'MD',
            endCountry: 'US'
        }
    ]

    function changeInfoMap(inputKey, inputValue) {
        infoMap.set(inputKey, inputValue)
    }

    function getKeysFromMapArr() {
        let arr = []
        for (let [key, value] of myMap) {
            arr.push(key)
        }
        return arr;
    }

    return (
        <div className={style.accountComponent}>
            <div className={style.upperPannel}>
                <div className={style.logo}>
                    <NavLink to='/'> <img src={logo}/></NavLink>
                </div>
                <div>
                    <img src={user}/>
                </div>
            </div>
            <div className={style.accountComponent}>
                <div>
                    <div className={style.userInfo}>
                        <div className={style.title}>
                            Personal Info
                        </div>
                        <div className={style.info}>
                            <div>{state ? (
                                <form onSubmit={onClickSave}>
                                    <div className={style.form}>
                                        <div className={style.partOfInputInfo}>
                                            <InputForm value={""} keyOf={"firstName"} type={"text"} title={"First name"}
                                                       placeholder={state.get("firstName")}
                                                       changeState={changeInfoMap}/>
                                            <InputForm value={""} keyOf={"lastName"} type={"text"} title={"Last name"}
                                                       placeholder={state.get("lastName")} changeState={changeInfoMap}/>
                                            <InputForm keyOf={"dateOfBirth"}  type={"date"}
                                                       title={"Date of birth"}
                                                       // placeholder={state.get("dateOfBirth")}
                                                       changeState={changeInfoMap}/>
                                            <InputForm  keyOf={"email"} type={"email"} title={"E-mail"}
                                                       placeholder={state.get("email")} changeState={changeInfoMap}/>
                                        </div>
                                        <div className={style.partOfInputInfo}>
                                            <InputForm keyOf={"mobilePhone"} type={"tel"}
                                                       title={"Mobile phone"}
                                                       notFound={false} keyOf={"mobilePhone"}
                                                       placeholder={state.get("mobilePhone")}
                                                       changeState={changeInfoMap}/>
                                            <InputForm keyOf={"password"} type={"text"} title={"Passport"}
                                                       keyOf={"passport"} placeholder={state.get("passport")}
                                                       changeState={changeInfoMap}/>
                                            <InputForm value={state.get("citizenship")} notFound={false}
                                                       keyOf={"citizenship"} type={"countryPicker"}
                                                       changeState={changeInfoMap} title={"Citizenship"}
                                                       array={getKeysFromMapArr()}/>
                                            <InputForm value={state.get("currentCountry")} type={"countryPicker"}
                                                       title={"Current country"} notFound={false}
                                                       keyOf={"currentCountry"}
                                                       changeState={changeInfoMap}
                                                       array={getKeysFromMapArr()}/>
                                        </div>
                                    </div>
                                    <button type={"submit"} className={style.createButton}>
                                        Save
                                    </button>
                                </form>
                            ) : null}
                            </div>
                        </div>
                        <div className={style.line}></div>
                        <div className={style.containerScroll}>
                            <Scrollable _class={style.items}>
                                {
                                    cards.map((card) => {
                                        return (
                                            <div key={card.id}>
                                                <SavedRoute title={card.title} startCountry={card.from}
                                                            endCountry={card.to} id={card.id}/>
                                            </div>
                                        )
                                    })
                                }
                            </Scrollable>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
    /*return (
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
                        <div className={style.cardsContainer}>
                            {
                                cards.length ? cards.map((card) =>
                                    <div key={card.id} className={style.rectangle}>
                                        <div className={style.cardsContainer}>
                                            <div style={{margin: "auto"}}>{card.title}</div>
                                            <div data-index={card.id} onClick={onClickS}
                                                 style={{marginRight: "40px"}}>x
                                            </div>
                                        </div>
                                        <div style={{margin: "auto"}} className={style.cardsContainer}>
                                            <div>{card.from}</div>
                                            <div className={style.cardLine}></div>
                                            <div>{card.to}</div>
                                        </div>
                                        <button data-index={card.id} onClick={onClickG} className={style.createButton}>
                                            Go to route
                                        </button>
                                    </div>
                                ) : null
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )*/
}

export default Account;

