import React, {useState, useEffect}  from 'react'
import style from './Admin.module.css';
import Logo from '../MainPage/Logo/Logo'
import {NavLink,Redirect} from "react-router-dom";
import ReactTable from 'react-table';

function Admin(){

    const [data,setData] = useState([]);

    useEffect(() => {
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        };
        fetch("http://localhost:8080/api/v1/admin/get", requestOptions).then(response => response.json().then(data => ({
            data: data,
            status: response.status })
        ).then(res => {
            if (res.data!==""){
                console.log(data);
            }
        }));
    },[]);

    const columns = [{
        Header: 'Country name',
        accessor: 'Country name'
    }, {
        Header: 'Status',
        accessor: 'Status'
    }]

    return(
        <div className = {style.container}>
            <Logo/>
            <div className = {style.navigationBar}>
                <nav>
                    <ul className = {style.navigation}>
                        <li style = {{paddingRight:"30px"}}>Country</li>
                        <li style = {{paddingRight:"30px"}}>User</li>
                        <li style = {{paddingRight:"30px"}}>Document</li>
                        <li style = {{paddingRight:"30px"}}>Status</li>
                        <li style = {{paddingRight:"30px"}}>Consulate</li>
                        <li style = {{paddingRight:"30px"}}>Have document</li>
                        <li style = {{paddingRight:"30px"}}>Medicine</li>
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

        </div>
    );

}

export default Admin;