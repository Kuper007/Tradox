import styles from './SearchBar.module.css'
const SearchBar = () =>{
        return (
            <div className = {styles.container}>
                <input type = "text" placeholder="Country Name..." name = 'search' className = {styles.searcher}/>
            </div>
        );
    }




export default SearchBar
