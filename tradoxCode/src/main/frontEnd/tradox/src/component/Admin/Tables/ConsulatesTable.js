import React from 'react'
import style from './Tables.module.css'
function ConsulatesTable(props) {
    return (
        <div>
            <table >
                <thead>
                <tr>
                    <th><input type ="checkbox" value ={false}></input></th>
                    <th>ID</th>
                    <th>Owner Country</th>
                    <th>Location Country</th>
                    <th>City</th>
                    <th>Address</th>
                    <th>Phone</th>
                </tr>
                </thead>
                <tbody >
                {
                    Object.keys(props.consulates)
                        .map(consulate =>
                            <tr key={props.consulates[consulate].consulateId}>
                                <th><input className={style.fields} type ="checkbox" defaultChecked={false}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.consulates[consulate].consulateId}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.consulates[consulate].owner.shortName}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.consulates[consulate].country.shortName}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.consulates[consulate].city}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.consulates[consulate].address}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.consulates[consulate].phoneNumber}></input></th>
                            </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default ConsulatesTable