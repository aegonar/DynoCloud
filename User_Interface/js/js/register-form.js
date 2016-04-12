var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var TextInput = require('./components/TextInput.js');

var RegisterUser = React.createClass({

  getInitialState: function () {
    return {
      firstname: null,
      lastname: null,
      username: null,
      email: null,
      confirmEmail: null,
      password: null,
      confirmPassword: null,
      phone: null
    }
  },

  isEmpty: function (value) {
    return !_.isEmpty(value);
  },

  validateEmail: function (event) {
    // regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
    var regx = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regx.test(event);
  },

  validatePhone: function(event) {
    var regx = /^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/;
    return regx.test(event);
  },

  validatePassword: function(event) {

    return null
  },

  handleFirstNameInput: function(event){
    this.setState({
      firstname: event.target.value
    });
  },

  handleLastNameInput: function(event){
    this.setState({
      lastname: event.target.value
    });
  },

  handleUsernameInput: function(event){
    this.setState({
      username: event.target.value
    });
  },

  handleEmailInput: function(event){
    if(!_.isEmpty(this.state.confirmEmail)){
      this.refs.emailConfirm.isValid();
    }
    this.refs.emailConfirm.hideError();
    this.setState({
      email: event.target.value
    });
  },

  handleConfirmEmailInput: function(event){
    this.setState({
      confirmEmail: event.target.value
    });
  },

  handlePasswordInput: function (event) {
    if(!_.isEmpty(this.state.confirmPassword)){
      this.refs.passwordConfirm.isValid();
    }
    this.refs.passwordConfirm.hideError();
    this.setState({
      password: event.target.value
    });
  },

  handleConfirmPasswordInput: function (event) {
    this.setState({
      confirmPassword: event.target.value
    });
  },

  isConfirmedPassword: function (event) {
    return (event == this.state.password)
  },

  isConfirmedEmail: function (event) {
    return (event == this.state.email)
  },

  handlePhoneInput: function(event){
    this.setState({
      phone: event.target.value
    });
  },

  saveAndRegister: function (e) {
    e.preventDefault();

    var canProceed = this.validateEmail(this.state.email) 
        && this.validateEmail(this.state.confirmEmail)
        && this.validatePhone(this.state.phone)
        && !_.isEmpty(this.state.firstname)
        && !_.isEmpty(this.state.lastname)
        && !_.isEmpty(this.state.username)
        && this.refs.password.isValid()
        && this.refs.passwordConfirm.isValid();

    if(canProceed) {
      var data = {
        firstname: this.state.firstname,
        lastname: this.state.lastname,
        username: this.state.username,
        email: this.state.email,
        phone: this.state.phone
      }
      alert('Welcome to DynoCloud');
    } else {
      this.refs.firstname.isValid();
      this.refs.lastname.isValid();
      this.refs.username.isValid();
      this.refs.email.isValid();
      this.refs.emailConfirm.isValid();
      this.refs.password.isValid();
      this.refs.passwordConfirm.isValid();
    }
  },

  render: function() {
      return (
        <form role="form" onSubmit={this.saveAndRegister}>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="firstname"
              placeholder = "Name *"
              validate={this.isEmpty}
              value={this.state.firstname}
              onChange={this.handleFirstNameInput} 
              emptyMessage="First name cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="lastname"
              placeholder = "Last Name *"
              validate={this.isEmpty}
              value={this.state.lastname}
              onChange={this.handleLastNameInput} 
              emptyMessage="Last name cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="username"
              placeholder = "Username *"
              validate={this.isEmpty}
              value={this.state.username}
              onChange={this.handleUsernameInput} 
              emptyMessage="Username cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput
              className="form-control" 
              type="text" 
              ref="email"
              placeholder = "Email Address *"
              validate={this.validateEmail}
              value={this.state.email}
              onChange={this.handleEmailInput} 
              errorMessage="Email format is invalid."
              emptyMessage="Email cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="emailConfirm"
              placeholder = "Confirm Email Address *"
              validate={this.isConfirmedEmail}
              value={this.state.confirmEmail}
              onChange={this.handleConfirmEmailInput}
              errorMessage="Emails do not match."
              emptyMessage="Please confirm your email."/>
          </div>

          <div className="form-group">
            <TextInput  
              className="form-control" 
              type="password" 
              ref="password"
              placeholder = "Password *"
              validator="true"
              minCharacters="8"
              requireCapitals="1"
              requireNumbers="1"
              validate={this.isEmpty}
              value={this.state.passsword}
              onChange={this.handlePasswordInput}
              errorMessage="Password is not valid."
              emptyMessage="Password cannot be empty." />
          </div>

          <div className="form-group">
            <TextInput  
              className="form-control" 
              type="password" 
              ref="passwordConfirm"
              placeholder = "Confirm Password *"
              validate={this.isConfirmedPassword}
              value={this.state.confirmPassword}
              onChange={this.handleConfirmPasswordInput} 
              emptyMessage="Please confirm your password."
              errorMessage="Passwords do not match."/>
          </div>

          <div className="form-group">
            <TextInput  
              className="form-control" 
              type="text" 
              ref="phone"
              placeholder = "Phone Number" 
              validate={this.validatePhone}
              value={this.state.phone}
              onChange={this.handlePhoneInput} 
              errorMessage="Phone format is invalid."/>
          </div>

          <div className="modal-footer">
            <button className="btn btn-default" data-dismiss="modal">Cancel</button>
            <button className="btn btn-primary" type="submit"> Register</button>
          </div>
        </form>
      );
  }    
});
      
ReactDOM.render(<RegisterUser/>, document.getElementById('register-form'))