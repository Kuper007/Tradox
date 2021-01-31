import React from 'react';
import styles from './SearchBar.module.css'
import React from 'react'

function SearchBar(props){


        return (
            <div className = {styles.container}>
                <input type = "text" placeholder="Country Name..." name = 'search' className = {styles.searcher}  onChange = {props.handleCountry} onKeyPress = {props.handleKeyPress}/>
            </div>
        );
    }




export default SearchBar
