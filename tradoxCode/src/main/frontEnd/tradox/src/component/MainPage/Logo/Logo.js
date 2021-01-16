import React from 'react';
import style from './Logo.module.css'
import logo from '../../../images/LogoTradoxLogo.svg';
import user from '../../../images/user.svg';
import vector from '../../../images/Vector.svg';
// import { NavLink } from 'react-router-dom';

class Logo extends React.Component {
    reloadPage () {
        window.location.reload()
      }
      render(){
    return (
        <div className = {style.container}>
            <img src={logo} onClick={() => this.reloadPage()} alt="logo" className = {style.logo}/>
            <div className = {style.auth}>
                <div className = {style.hidable}>
                    <a className = {`${style.register} ${style.link}`} href= '/register'>Register</a>
                    <a className = {`${style.logIn} ${style.link}`} href = '/login'>Log in</a>
                    <img src = {vector} alt = 'vector' className = {style.vector}/>
                </div>
                <img src={user}  alt="user" className = {style.user}/>
            </div>
        </div>
    )
    }
}
    



export default Logo
