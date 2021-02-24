import React from 'react'
import style from './Tables.module.css'
function CountryTable(props) {
    return (
        <div>
            <table >
                <thead>
                <tr>
                    <th><input type ="checkbox" value ={false}></input></th>
                    <th>Short name</th>
                    <th>Long name</th>
                    <th>Medium Bill in USD</th>
                    <th>Tourist count</th>
                    <th>Local currency</th>
                </tr>
                </thead>
                <tbody >
                {
                    Object.keys(props.countries)
                        .map(country =>
                            <tr key={props.countries[country].shortName}>
                                <th><input className={style.fields} type ="checkbox" value ={false}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.countries[country].shortName}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.countries[country].fullName}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.countries[country].mediumBill}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.countries[country].tourismCount}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.countries[country].currency}></input></th>
                            </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default CountryTable