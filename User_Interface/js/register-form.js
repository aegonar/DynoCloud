var React = require('react/addons');
var _ = require('underscore');
var Select = require('./components/Select');

var RegisterUser = React.createClass({
  getInitialState: function () {
    return {
    	name: null,
    	lastname: null,
    	username: null,
    	email: null,
    	confirmEmail: null,
    	password: null,
      	confirmPassword: null,
      	phone: null,
      	forbiddenWords: ["password", "user", "username", "12345678"]
      }
	},

	/* Verifying if password field is not empty. */
  	passwordHandling: function (event) {
    	if(!_.isEmpty(this.state.confirmPassword)){
      		this.refs.passwordConfirm.isValid();
    	}

    	this.refs.passwordConfirm.hideError();
    	this.setState({
      		password: event.target.value
    	});
  	},

  	confirmPasswordHandling: function (event) {
    	this.setState({
      		confirmPassword: event.target.value
    	});
  	},

  	register: function (e) {
    	e.preventDefault();

    	var canProceed = this.validateEmail(this.state.email) 
    		&& this.validateEmail(this.state.emailConfirm)
	        && !_.isEmpty(this.state.name)
	        && !_.isEmpty(this.state.lastname)
	        && !_.isEmpty(this.state.username)
	        && this.refs.password.isValid()
	        && this.refs.passwordConfirm.isValid();

    	if(canProceed) {
	      var data = {
	      	name: this.state.name,
	      	lastname: this.state.lastname,
	      	username: this.state.username,
	        email: this.state.email,
	        phone: this.state.phone,
	      }
	      alert('Thanks.');
	    } else {
	      this.refs.name.isValid();
	      this.refs.lastname.isValid();
	      this.refs.username.isValid();
	      this.refs.email.isValid();
	      this.refs.emailConfirm.isValid();
	      this.refs.password.isValid();
	      this.refs.passwordConfirm.isValid();
	      this.refs.phone.isValid();
	    }
	  },

	  isConfirmedPassword: function (event) {
	    return (event == this.state.password)
	  },

	  handleNameInput: function(event) {
	    this.setName({
	      name: event.target.value
	    })
	  },

	   handleLastNameInput: function(event) {
	    this.setLastName({
	      lastname: event.target.value
	    })
	  },

	   handleUsernameInput: function(event) {
	    this.setUsername({
	      username: event.target.value
	    })
	  },

	  handleEmailInput: function(event){
	    this.setState({
	      email: event.target.value
	    });
	  },

	  validateEmail: function (event) {
	    // regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
	    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(event);
	  },

	  isEmpty: function (value) {
	    return !_.isEmpty(value);
	  },

	   handlePhoneInput: function(event) {
	    this.setPhone({
	      phone: event.target.value
	    })
	  },

	  render: function() {
	    return (
	      <div class="modal fade" id="registerModal" role="dialog">
      		<div class="modal-dialog">
        		<div class="modal-content">
          			<div class="modal-header">
            			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	          			<h4 class="modal-title">Register User Account</h4>
	          		</div>
	          		<div class="modal-body">

	          		<form onSubmit={this.register} role="form" method="POST">

		            <input 
		            	class="form-control" 
		            	type="text" 
		            	ref="name"
		            	placeholder = "Name *"
		              	defaultValue={this.state.name} 
		              	validate={this.isEmpty}
		              	value={this.state.name}
		              	onChange={this.handleNameInput} 
		              	emptyMessage="Email can't be empty"
		            >

		            <input 
		            	class="form-control" 
		            	type="text" 
		            	ref="lastname"
		            	placeholder = "Last Name *"
		              	defaultValue={this.state.lastname} 
		              	validate={this.isEmpty}
		              	value={this.state.lastname}
		              	onChange={this.handleLastNameInput} 
		              	emptyMessage="Last Name can't be empty"
		            >

		            <input 
		            	class="form-control" 
		            	type="text" 
		            	ref="username"
		            	placeholder = "Username *"
		              	defaultValue={this.state.username} 
		              	validate={this.isEmpty}
		              	value={this.state.username}
		              	onChange={this.handleUsernameInput} 
		              	emptyMessage="Username can't be empty"
		            >

		            <input
		            	class="form-control"
		            	type="text"
	              		ref="email"
	              		placeholder="Email Address *"
	              		defaultValue={this.state.email} 
	              		validate={this.validateEmail}
	              		value={this.state.email}
	              		onChange={this.handleEmailInput} 
	              		errorMessage="Email is invalid"
	              		emptyMessage="Email can't be empty"
	              		errorVisible={this.state.showEmailError}
	              	>

	              	<input
		            	class="form-control"
		            	type="text"
	              		ref="emailConfirm"
	              		placeholder="Confirm Email *"
	              		validate={this.isConfirmedEmail}
	              		value={this.state.confirmEmail}
	              		onChange={this.handleConfirmEmailInput} 
	              		errorMessage="Emails don't match"
	              		emptyMessage="Please confirm email"
	              	>

		            <input 
		              class="form-control" 
		              type="password"
		              ref="password"
		              placeholder="Password *"
		              validator="true"
		              minCharacters="8"
		              requireCapitals="1"
		              requireNumbers="1"
		              forbiddenWords={this.state.forbiddenWords}
		              value={this.state.passsword}
		              emptyMessage="Password field is empty"
		              onChange={this.handlePasswordInput} 
		            >

		            <input
		            	class="form-control"
		            	type="password"
		              	ref="passwordConfirm"
		              	placeholder="Confirm Password *"
		              	validate={this.isConfirmedPassword}
		              	value={this.state.confirmPassword}
		              	onChange={this.handleConfirmPasswordInput} 
		              	emptyMessage="Please confirm your password"
		              	errorMessage="Passwords don't match"
		            > 

		            <input 
		            	class="form-control" 
		            	type="number" 
		            	ref="phone"
		            	placeholder = "Phone Number (ex. 9999999999)"
		            	minCharacters="10"
		              	defaultValue={this.state.phone} 
		              	value={this.state.phone}
		              	onChange={this.handlePhoneInput} 
		            >

		            <div class="modal-footer">
		            	<a class="btn btn-default" data-dismiss="modal">Cancel</a>
		            	<a class="btn btn-primary" type="submit"> Register</a>
		            </div>
		          </form>
	          </div>
	        </div>
	      </div>
	    );
	  }
	    
	});
	    
module.exports = RegisterUser;