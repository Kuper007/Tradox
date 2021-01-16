import React, {useState}  from 'react'
import style from './Register.module.css';
import logo from '../../images/LogoTradoxLogo.svg';
function Register(props) {
    // const [state , setState] = useState({
    //     first_name : "",
    //     last_name : "",
    //     dob: "",
    //     email: "",
    //     password: "",
    //     phone: "",
    //     passport: "",
    //     conviction: ""
    // })
    // const handleChange = (e) => {
    //     const {id , value} = e.target
    //     setState(prevState => ({
    //         ...prevState,
    //         [id] : value
    //     }))
    // }
    // const handleSubmitClick = (e) => {
    //     e.preventDefault();
    //     if(state.password === state.confirmPassword) {
    //         sendDetailsToServer()
    //     } else {
    //         props.showError('Passwords do not match');
    //     }
    // }
    // const sendDetailsToServer = () => {
    //     if(state.email.length && state.password.length) {
    //         props.showError(null);
    //         const payload={
    //             "em0ail":state.email,
    //             "password":state.password,
    //         }
    //         axios.post(API_BASE_URL+'/user/register', payload)
    //             .then(function (response) {
    //                 if(response.status === 200){
    //                     setState(prevState => ({
    //                         ...prevState,
    //                         'successMessage' : 'Registration successful. Redirecting to home page..'
    //                     }))
    //                     redirectToHome();
    //                     props.showError(null)
    //                 } else{
    //                     props.showError("Some error ocurred");
    //                 }
    //             })
    //             .catch(function (error) {
    //                 console.log(error);
    //             });
    //     } else {
    //         props.showError('Please enter valid username and password')
    //     }
    //
    // }
    return (
        <div className = {style.container}>
            <img src={logo} onClick={() => this.reloadPage()} alt="logo" />
            <h2>Registration</h2>
            <form className = {style.regForm}>
            <label>
                First name:
                <input type="text" id="first_name" />
            </label>
            <label>
                Last name:
                <input type="text" id="last_name" />
            </label>
            <label>
                Date of birth:
                <input type="text" id="dob" />
            </label>
            <label>
                E-mail:
                <input type="text" id="email" />
            </label>
            <label>
                Password:
                <input type="password" id="password" />
            </label>
            <label>
                Mobile phone:
                <input type="text" id="phone" />
            </label>
            <label>
                Passport:
                <input type="text" id="passport" />
            </label>
            <label>
                Conviction:
                <input type="text" id="conviction" />
            </label>
            </form>
            <button className = {style.registerBtn}/> Register <button/>
        </div>
    )
}

export default Register

