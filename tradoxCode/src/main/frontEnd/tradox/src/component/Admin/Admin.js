 import React, {useState}  from 'react'
import style from './Admin.module.css';
import Logo from '../MainPage/Logo/Logo'
import axios from "axios";
import CountryTable from "./Tables/CountriesTable";
import UsersTable from "./Tables/UsersTable";
 import DocumentsTable from "./Tables/DocumentsTable";
 import StatusTable from "./Tables/StatusTable";
 import ConsulatesTable from "./Tables/ConsulatesTable";
 import HaveDocTable from "./Tables/HaveDocTable";
 import MedicineTable from "./Tables/MedicineTable";

function Admin(){

    const [isAuth, setIsAuth] = useState(true);
    const [isAdmin, setIsAdmin] = useState(true);

    const [countries,setCountries] = useState([]);
    const [users,setUsers] = useState([]);
    const [documents,setDocuments] = useState([]);
    const [status,setStatus] = useState([]);
    const [consulate,setConsulate] = useState([]);
    const [haveDocument,setHaveDocument] = useState([]);
    const [medicine,setMedicine] = useState([]);
    const [showUsers, setShowUsers] = useState(false)
    const [showCountries, setShowCountries] = useState(false)
    const [showDocuments, setShowDocuments] = useState(false)
    const [showStatus, setShowStatus] = useState(false)
    const [showConsulate, setShowConsulate] = useState(false)
    const [showHaveDocument, setShowHaveDocument] = useState(false)
    const [showMedicine, setShowMedicine] = useState(false)
    function getCountries(){
        try{
            axios.get("http://localhost:8080/api/v1/admin/getCountries").then(res => {
                console.log(res.data)
                if (res.status === 200){
                    setCountries(res.data.object)
                    setShowCountries(true);
                    setShowConsulate(false)
                    setShowDocuments(false)
                    setShowHaveDocument(false)
                    setShowUsers(false)
                    setShowStatus(false)
                }
                else{
                }
            })
        }

        catch (e){
            console.log(`😱 Axios request failed: ${e}`);
        }
    }
    function getUsers(){
        try{
            axios.get("http://localhost:8080/api/v1/admin/getUsers").then(res => {
                console.log(res.data)
                if (res.status === 200){
                    setUsers(res.data.object)
                    setShowMedicine(false)
                    setShowCountries(false);
                    setShowConsulate(false)
                    setShowDocuments(false)
                    setShowHaveDocument(false)
                    setShowUsers(true)
                    setShowStatus(false)
                }
                else{
                }
            })
        }

        catch (e){
            console.log(`😱 Axios request failed: ${e}`);
        }
    }
    function getDocuments(){
        try{
            axios.get("http://localhost:8080/api/v1/admin/getDocuments").then(res => {
                console.log(res.data)
                if (res.status === 200){
                    setDocuments(res.data.object)
                    setShowMedicine(false)
                    setShowCountries(false);
                    setShowConsulate(false)
                    setShowDocuments(true)
                    setShowHaveDocument(false)
                    setShowUsers(false)
                    setShowStatus(false)
                }
                else{
                }
            })
        }

        catch (e){
            console.log(`😱 Axios request failed: ${e}`);
        }
    }
    function getConsulates(){
        try{
            axios.get("http://localhost:8080/api/v1/admin/getConsulates").then(res => {
                console.log(res.data)
                if (res.status === 200){
                    setConsulate(res.data.object)
                    setShowMedicine(false)
                    setShowCountries(false);
                    setShowConsulate(true)
                    setShowDocuments(false)
                    setShowHaveDocument(false)
                    setShowUsers(false)
                    setShowStatus(false)
                }
                else{
                }
            })
        }

        catch (e){
            console.log(`😱 Axios request failed: ${e}`);
        }
    }
    function getHaveDocument(){
        try{
            axios.get("http://localhost:8080/api/v1/admin/getCountryDocuments").then(res => {
                console.log(res.data)
                if (res.status === 200){
                    setHaveDocument(res.data.object)
                    setShowMedicine(false)
                    setShowCountries(false);
                    setShowConsulate(false)
                    setShowDocuments(false)
                    setShowHaveDocument(true)
                    setShowUsers(false)
                    setShowStatus(false)
                }
                else{
                }
            })
        }

        catch (e){
            console.log(`😱 Axios request failed: ${e}`);
        }
    }
    function getMedicine(){
        try{
            axios.get("http://localhost:8080/api/v1/admin/getMedicines").then(res => {
                console.log(res.data)
                if (res.status === 200){
                    setMedicine(res.data.object)
                    setShowMedicine(true)
                    setShowCountries(false);
                    setShowConsulate(false)
                    setShowDocuments(false)
                    setShowHaveDocument(false)
                    setShowUsers(false)
                    setShowStatus(false)
                }
                else{
                }
            })
        }

        catch (e){
            console.log(`😱 Axios request failed: ${e}`);
        }
    }
    function getStatus(){
        try{
            axios.get("http://localhost:8080/api/v1/admin/getStatuses").then(res => {
                console.log(res.data)
                if (res.status === 200){
                    setStatus(res.data.object)
                    setShowMedicine(false)
                    setShowCountries(false);
                    setShowConsulate(false)
                    setShowDocuments(false)
                    setShowHaveDocument(false)
                    setShowUsers(false)
                    setShowStatus(true)
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
        <div className = {style.container}>
            <Logo authorized = {isAuth} admin={isAdmin}/>
            <div className = {style.navigationBar}>
                <nav>
                    <ul className = {style.navigation}>
                        <li style = {!showCountries?{paddingRight:"30px"}:{paddingRight:"30px", color:"#000000"}} onClick={getCountries}>Country</li>
                        <li style = {!showUsers?{paddingRight:"30px"}:{paddingRight:"30px", color:"#000000"}} onClick={getUsers}>User</li>
                        <li style = {!showDocuments?{paddingRight:"30px"}:{paddingRight:"30px", color:"#000000"}} onClick={getDocuments}>Document</li>
                        <li style = {!showStatus?{paddingRight:"30px"}:{paddingRight:"30px", color:"#000000"}} onClick={getStatus}>Status</li>
                        <li style = {!showConsulate?{paddingRight:"30px"}:{paddingRight:"30px", color:"#000000"}} onClick={getConsulates}>Consulate</li>
                        <li style = {!showHaveDocument?{paddingRight:"30px"}:{paddingRight:"30px", color:"#000000"}} onClick={getHaveDocument}>Have document</li>
                        <li style = {!showMedicine?{paddingRight:"30px"}:{paddingRight:"30px", color:"#000000"}} onClick={getMedicine}>Medicine</li>
                    </ul>
                </nav>
            </div>
            <div className={style.buttons}>
                <button className={style.selected}>Save Selected</button>
                <button className={style.save}>Save All</button>
                <button className={style.new}>New</button>
                <button className={style.delete}>Delete</button>
                <input type="text" placeholder = "Search by full name or short name" className={style.search}/>
            </div>
            <div className={style.tables}>
                {showCountries?<CountryTable countries = {countries}/>:null}
                {showUsers?<UsersTable users = {users}/>:null}
                {showDocuments?<DocumentsTable docs = {documents}/>:null}
                {showStatus?<StatusTable statuses = {status}/>:null}
                {showConsulate?<ConsulatesTable consulates = {consulate}/>:null}
                {showHaveDocument?<HaveDocTable haveDoc = {haveDocument}/>:null}
                {showMedicine?<MedicineTable medicines = {medicine}/>:null}
            </div>
        </div>
    );

}

export default Admin;