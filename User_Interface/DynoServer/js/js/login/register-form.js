var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var Router = require('react-router');
var TextInput = require('../components/text-input.js');

var RegisterUser = React.createClass({

  getInitialState: function () {
    return {
      name: null,
      lastName: null,
      userName: null,
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
      name: event.target.value
    });
  },

  handleLastNameInput: function(event){
    this.setState({
      lastName: event.target.value
    });
  },

  handleUsernameInput: function(event){
    this.setState({
      userName: event.target.value
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

  handleSubmitRegistration: function (e) {
    e.preventDefault();

    var canProceed = this.validateEmail(this.state.email) 
        && this.validateEmail(this.state.confirmEmail)
        && !_.isEmpty(this.state.name)
        && !_.isEmpty(this.state.lastName)
        && !_.isEmpty(this.state.userName)
        && this.refs.password.isValid()
        && this.refs.passwordConfirm.isValid();

    if(canProceed) {

      var regData = {
        name: this.state.name,
        lastName: this.state.lastName,
        userName: this.state.userName,
        email: this.state.email,
        password: this.state.password,
        phone: this.state.phone
      }

      var url = 'http://dynocare.xyz/api/user';


      jQuery.ajax({
        url: url,
        dataType: 'json',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify( regData ),
        
        success: function(data, textStatus, jqXHR){
          alert('Welcome to DynoCloud!');
        },
        error: function(jqXHR, textStatus, errorThrown){
          console.error(url, status, errorThrown.toString());
        }.bind(this),
        complete: this.handleRegistration
      });
    } 
    else {
      this.refs.name.isValid();
      this.refs.lastName.isValid();
      this.refs.userName.isValid();
      this.refs.email.isValid();
      this.refs.emailConfirm.isValid();
      this.refs.password.isValid();
      this.refs.passwordConfirm.isValid();
    }
  },

  handleRegistration: function(){
    window.location.reload(true);
    jQuery(document.getElementById('registerModal')).modal('toggle');
  },

  render: function() {
      return (
        <form id="reg-form" ref="regForm" role="form" onSubmit={this.handleSubmitRegistration} method="POST">

          <div className="form-group">
            <label className="control-label">Name
            </label>
            <TextInput 
              className="form-control" 
              type="text" 
              ref="name"
              placeholder = "Name *"
              validate={this.isEmpty}
              value={this.state.name}
              onChange={this.handleFirstNameInput} 
              emptyMessage="First name cannot be empty."/>
          </div>

          <div className="form-group">
            <label className="control-label">Last Name
            </label>
            <TextInput 
              className="form-control" 
              type="text" 
              ref="lastName"
              placeholder = "Last Name *"
              validate={this.isEmpty}
              value={this.state.lastName}
              onChange={this.handleLastNameInput} 
              emptyMessage="Last name cannot be empty."/>
          </div>

          <div className="form-group">
            <label className="control-label">Username
            </label>
            <TextInput 
              className="form-control" 
              type="text" 
              ref="userName"
              placeholder = "Username *"
              validate={this.isEmpty}
              value={this.state.userName}
              onChange={this.handleUsernameInput} 
              emptyMessage="Username cannot be empty."/>
          </div>

          <div className="form-group">
            <label className="control-label">Email Address
            </label>
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
            <label className="control-label">Confirm Email Address
            </label>
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
            <label className="control-label">Password
            </label>
            <TextInput  
              className="form-control" 
              type="password" 
              ref="password"
              placeholder = "Password *"
              validate={this.isEmpty}
              value={this.state.passsword}
              onChange={this.handlePasswordInput}
              errorMessage="Password is not valid."
              emptyMessage="Password cannot be empty." />
          </div>

          <div className="form-group">
            <label className="control-label">Confirm Password
            </label>
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
            <label className="control-label">Phone Number
            </label>
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
            <button ref="cancel" className="btn btn-default" data-dismiss="modal">Cancel</button>
            <button id="register" ref="submit" className="btn btn-primary" type="submit">Register</button>
          </div>
        </form>
      );
  }    
});
      
ReactDOM.render(<RegisterUser/>, document.getElementById('register-form'))