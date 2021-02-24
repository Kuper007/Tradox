import React from 'react'
import style from './Tables.module.css'
function MedicineTable(props) {
    return (
        <div>
            <table >
                <thead>
                <tr>
                    <th><input type ="checkbox" value ={false}></input></th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Country</th>
                </tr>
                </thead>
                <tbody >
                {
                    Object.keys(props.medicines)
                        .map(medicine =>
                            <tr key={props.medicines[medicine].medicineId}>
                                <th><input className={style.fields} type ="checkbox" value ={false}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.medicines[medicine].medicineId}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.medicines[medicine].name}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.medicines[medicine].country.shortName}></input></th>
                              </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default MedicineTable