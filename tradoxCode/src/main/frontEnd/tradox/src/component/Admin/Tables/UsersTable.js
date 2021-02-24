import React from 'react'
import style from './Tables.module.css'
function UsersTable(props) {
    return (
        <div>
            <table >
                <thead>
                <tr>
                    <th><input type ="checkbox" value ={false}></input></th>
                    <th>ID</th>
                    <th>User type</th>
                    <th>First name</th>
                    <th>Last name</th>
                    <th>Birth date</th>
                    <th>Email</th>
                    <th>Verify</th>
                    <th>Phone</th>
                    <th>Location</th>
                </tr>
                </thead>
                <tbody >
                {
                    Object.keys(props.users)
                        .map(user =>
                            <tr key={props.users[user].id}>
                                <th><input className={style.fields} type ="checkbox" value ={false}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].userId}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].userType}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].firstName}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].lastName}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].birthDate}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].email}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].verify}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].phone}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.users[user].location.shortName}></input></th>


                            </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default UsersTable