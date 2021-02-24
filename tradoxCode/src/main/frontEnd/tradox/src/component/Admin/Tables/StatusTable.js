import React from 'react'
import style from './Tables.module.css'
function StatusTable(props) {
    return (
        <div>
            <table >
                <thead>
                <tr>
                    <th><input type ="checkbox" value ={false}></input></th>
                    <th>ID</th>
                    <th>Status</th>
                    <th>Departure Country</th>
                    <th>Destination Country</th>
                    <th>Covid reason</th>
                    <th>Citizenship reason</th>
                </tr>
                </thead>
                <tbody >
                {
                    Object.keys(props.statuses)
                        .map(status =>
                            <tr key={props.statuses[status].statusId}>
                                <th><input className={style.fields} type ="checkbox" value ={false}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.statuses[status].statusId}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.statuses[status].status}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.statuses[status].fullRoute.departure.shortName}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.statuses[status].fullRoute.destination.shortName}></input></th>
                                <th><input className={style.fields} type="checkbox" defaultChecked={props.statuses[status].reason.covidReason}></input></th>
                                <th><input className={style.fields} type="checkbox" defaultChecked={props.statuses[status].reason.citizenshipReason}></input></th>
                            </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default StatusTable