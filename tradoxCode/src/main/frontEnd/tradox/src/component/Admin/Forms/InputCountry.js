import React, {useState} from 'react'
import styles from "./InputCountry.module.css"
import axios from "axios";
function InputCountry(props){
    const [longName, setLongName]  = React.useState('')
    const [shortName, setShortName]  = React.useState('')
    const [currency, setCurrency]  = React.useState('')
    const [bill, setBill]  = React.useState('')
    const [count, setCount]  = React.useState('')
    function newCountry(){
        try{
            axios.post("http://localhost:8080/api/v1/admin/addCountry", { "shortName": shortName, "fullName" : longName, "currency": currency, "mediumBill": bill, "tourismCount": count}, {headers:{ 'Content-Type': 'application/json' }}).then(res => {
                console.log(res.data)
                if(res.status === 200){
                   alert("Added")
                }
            })
        }
        catch (e){
            console.log(`😱 Axios request failed: ${e}`);
        }
    }
    return(
            <div className = {styles.container}>
                <h1 className = {styles.title}>New Country</h1>
                <form className = {styles.form}>
                    <div><span className={styles.naming}>Country</span><input id = "country" type = "text" value={longName} onChange={event => setLongName(event.target.value)} className={styles.input}/></div>
                    <div><span className={styles.naming}>Short name</span><input id = "shortName" type = "text" value={shortName} onChange={event => setShortName(event.target.value)} className={styles.input}/></div>
                    <div><span className={styles.naming}>Currency</span><input id = "currency" type = "text" value={currency} onChange={event => setCurrency(event.target.value)} className={styles.input}/></div>
                    <div><span className={styles.naming}>Bill</span><input id = "bill" type = "text" value={bill} onChange={event => setBill(event.target.value)} className={styles.input}/></div>
                    <div><span className={styles.naming}>Count</span><input id = "count" type = "text" value={count} onChange={event => setCount(event.target.value)} className={styles.input}/></div>
                </form>
                <div>
                    <input className = {styles.createBtn} type = "submit" value = "Create" onClick = {newCountry}/>
                    <input className = {styles.cancelBtn} id = "close" type = "button" value = "Cancel" onClick={props.handlePressCountry}/>
                </div>
            </div>
    )
}
export default InputCountry