import React from 'react'
import style from './InputForm.module.css'
import axios from 'axios'
function InputForm() {
    const [longName, setLongName]  = React.useState('')
    const [shortName, setShortName]  = React.useState('')
    const [currency, setCurrency]  = React.useState('')
    const [bill, setBill]  = React.useState('')
    const [count, setCount]  = React.useState('')

    function newCountry(){
        try{
            axios.post("http://localhost:8080/api/v1/admin/addCountry", { "shortName": shortName, "longName" : longName, "currency": currency, "mediumBill": bill, "tourismCount": count}, {headers:{ 'Content-Type': 'application/json' }}).then(res => {
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
    return (
        <div className = {style.container}>
            <h1 className = {style.title}>New Country</h1>
            <form className = {style.form}>
                <label htmlFor = "country">Full name</label>
                <input id = "country" type = "text" value={longName} onChange={event => setLongName(event.target.value)}/>
                <label htmlFor = "shortName">Short name</label>
                <input id = "shortName" type = "text" value={shortName} onChange={event => setShortName(event.target.value)}/>
                <label htmlFor = "currency">Currency</label>
                <input id = "currency" type = "text" value={currency} onChange={event => setCurrency(event.target.value)}/>
                <label htmlFor = "bill">Medium bill</label>
                <input id = "bill" type = "text" value={bill} onChange={event => setBill(event.target.value)}/>
                <label htmlFor = "count">Tourist count</label>
                <input id = "count" type = "text" value={count} onChange={event => setCount(event.target.value)}/>
                <div>
                    <input className = {style.createBtn} type = "submit" value = "Create" onClick = {newCountry()}/>
                    <input className = {style.cancelBtn} type = "button" value = "Cancel"/>
                </div>
            </form>
        </div>
    )
}

export default InputForm
