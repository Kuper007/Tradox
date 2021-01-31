import styles from './SearchBar.module.css'
import React from 'react'

function SearchBar(props){

    const before = styles.searcher
    const after  = styles.searcherShake

    return (
        <div className = {styles.container}>
            {!props.notfound? <input type = "text" placeholder="Country Name..." name = 'search' className = {before}  onChange = {props.handleCountry} onKeyPress = {props.handleKeyPress}/>
                :<input type = "text" placeholder="Country Name..." name = 'search' className = {after}  onChange = {props.handleCountry} onKeyPress = {props.handleKeyPress}/>}
        </div>
    );
}




export default SearchBar
