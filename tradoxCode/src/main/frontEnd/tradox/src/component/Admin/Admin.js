import React, {useState, useEffect}  from 'react'
import style from './Admin.module.css';
import logo from '../../images/LogoTradoxLogo.svg';
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
        <span>Admin Page</span>
        /*<ReactTable
            data = {data}
            columns = {columns}
        />*/
    );

}

export default Admin;