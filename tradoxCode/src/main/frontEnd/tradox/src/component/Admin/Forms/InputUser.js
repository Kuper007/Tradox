import React, {useState} from 'react'
import styles from "./InputCountry.module.css"
import axios from "axios";
function InputUser(){
    const [userType, setUserType]  = useState('')
    const [firstName, setFirstName]  = useState('')
    const [lastName, setLastName]  = useState('')
    const [birthDate, setBirthDate]  = useState('')
    const [email, setEmail]  = useState('')
    const [verify, setVerify]  = useState('')
    const [phone, setPhone]  = useState('')
    const [location, setLocation]  = useState('')

    function newCountry(){
        try{
            axios.post("http://localhost:8080/api/v1/admin/addUser", {}, {headers:{ 'Content-Type': 'application/json' }}).then(res => {
                console.log(res.data)
                if (res.status === 200){
                }
                else{
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
                <div><span className={styles.naming}>User type</span><input type = "text" value={userType} onChange={event => setUserType(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>First name</span><input type = "text" value={firstName} onChange={event => setFirstName(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>Last name</span><input  type = "text" value={lastName} onChange={event => setLastName(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>Birth date</span><input  type = "text" value={birthDate} onChange={event => setBirthDate(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>Email</span><input id = "count" type = "text" value={email} onChange={event => setEmail(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>Verify</span><input id = "count" type = "text" value={verify} onChange={event => setVerify(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>Phone</span><input id = "count" type = "text" value={phone} onChange={event => setPhone(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>Location</span><input id = "count" type = "text" value={location} onChange={event => setLocation(event.target.value)} className={styles.input}/></div>
                <div>
                    <input className = {styles.createBtn} type = "submit" value = "Create" onClick = {newCountry}/>
                    <input className = {styles.cancelBtn} type = "button" value = "Cancel"/>
                </div>
            </form>
        </div>
    )
}
export default InputUser