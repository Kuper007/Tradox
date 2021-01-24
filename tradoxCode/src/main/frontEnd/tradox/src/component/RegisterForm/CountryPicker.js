import style from './Register.module.css';

function Option(props) {
    return (
        <option>
            {props.title}
        </option>
    )
}

function Picker(props) {

    return (
        <select onChange={props.changeState} >
            {props.array.map((x,y) => <Option title={x} key={y}/>)}
        </select>
    )
}

export default Picker;
