import React, {useState} from 'react'
import styles from "./InputCountry.module.css"
import axios from "axios";
function InputDoc(props){
    const[description, setDescription] = useState('')
    const[name, setName] = useState('')
    const [fileLink, setFileLink] = useState('')
    function newDoc(){
        try{
            axios.post("http://localhost:8080/api/v1/admin/addCountry", {"description" : description, "name": name, "fileLink": fileLink}, {headers:{ 'Content-Type': 'application/json' }}).then(res => {
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
                <div><span className={styles.naming}>Country</span><input id = "country" type = "text" value={description} onChange={event => setDescription(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>Short name</span><input id = "shortName" type = "text" value={name} onChange={event => setName(event.target.value)} className={styles.input}/></div>
                <div><span className={styles.naming}>Currency</span><input id = "currency" type = "text" value={fileLink} onChange={event => setFileLink(event.target.value)} className={styles.input}/></div>
                </form>
            <div>
                <input className = {styles.createBtn} type = "submit" value = "Create" onClick = {newDoc}/>
                <input className = {styles.cancelBtn} id = "close" type = "button" value = "Cancel" onClick={props.handlePressCountry}/>
            </div>
        </div>
    )
}
export default InputDoc