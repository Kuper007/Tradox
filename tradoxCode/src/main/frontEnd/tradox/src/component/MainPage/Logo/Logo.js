import React from 'react';
import style from './Logo.module.css'
import logo from '../../../images/LogoTradoxLogo.svg';
import user from '../../../images/user.svg';
import vector from '../../../images/Vector.svg';
import {NavLink} from 'react-router-dom';

class Logo extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pressed: false
        };
    }
    showAuth = () => {
        if (this.state.pressed === false)
        this.setState({pressed: true});
        else this.setState({pressed:false})
    }
    reloadPage () {
        if(window.location === '/'){
        window.location.reload()}
        else{
        window.location.href = '/' 
        }
      }
      render(){
    return (
        <div className = {style.container}>
            <img src={logo} onClick={() => this.reloadPage()} alt="logo" className = {style.logo}/>
            <div className = {style.auth}>
            {this.state.pressed ?<div className = {style.hidable} >
                <NavLink className = {`${style.register} ${style.link}`} to= '/registration'>Register</NavLink>
                    <NavLink className = {`${style.logIn} ${style.link}`} to = '/login'>Log in</NavLink>
                    <img src = {vector} alt = 'vector' className = {style.vector}/>
                </div>: null}
                <img src={user}  alt="user" className = {style.user} onMouseDown={this.showAuth}/>
            </div>
        </div>
    )
    }
}
    



export default Logo
