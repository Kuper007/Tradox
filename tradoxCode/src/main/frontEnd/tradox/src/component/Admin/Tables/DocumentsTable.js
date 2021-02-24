import React from 'react'
import style from './Tables.module.css'
function DocumentsTable(props) {
    return (
        <div>
            <table >
                <thead>
                <tr>
                    <th><input type ="checkbox" value ={false}></input></th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody >
                {
                    Object.keys(props.docs)
                        .map(doc =>
                            <tr key={props.docs[doc].documentId}>
                                <th><input className={style.fields} type ="checkbox" value ={false}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.docs[doc].documentId}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.docs[doc].name}></input></th>
                                <th><input className={style.fields} type="text" defaultValue={props.docs[doc].description}></input></th>
                            </tr>)
                }
                </tbody>
            </table>
        </div>
    )
}

export default DocumentsTable