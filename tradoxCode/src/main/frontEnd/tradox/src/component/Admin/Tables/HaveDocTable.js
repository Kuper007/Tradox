import React from 'react'
import style from './Tables.module.css'
function HaveDocTable(props) {
    return (
        <div>
            <table >
                <thead>
                <tr>
                    <th><input type ="checkbox" value ={false}></input></th>
                    <th>ID</th>
                    <th>Document ID</th>
                    <th>Destination country</th>
                    <th>Departure country</th>
                </tr>
                </thead>
                <tbody >
                {
                    Object.keys(props.haveDoc)
                        .map(doc =>
                            <tr key={props.haveDoc[doc].id}>
                                <th><input className={style.fields} type ="checkbox" defaultChecked={false}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.haveDoc[doc].id}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.haveDoc[doc].documentId}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.haveDoc[doc].destination.shortName}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.haveDoc[doc].departure.shortName}></input></th>
                                 </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default HaveDocTable