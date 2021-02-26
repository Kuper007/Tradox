import React from 'react'
import style from './Tables.module.css'
function CountryTable(props) {
    const [selectedItems, setSelectedItems] = React.useState([])
    const handleOnclick = (event, index)=>{
        if(event.target.checked){
            setSelectedItems(value => [...value, index])
        }
        else{
            setSelectedItems(value => value.filter(v => v !== index))
        }
    }
    React.useEffect(() =>{
        console.log(Object.keys(props.countries).map(key =>{
            if(selectedItems.includes(key)){
                return props.countries[key]
            }
        else{
            return false
            }}).filter(value => value))
    },[selectedItems])
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
                <tbody>
                {
                    Object.keys(props.countries)
                        .map((country, index) =>
                            <tr key={props.countries[country].shortName}>
                                <th><input className={style.fields} type ="checkbox" value ={false} onClick={(event) =>{handleOnclick(event, country)}}></input></th>
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