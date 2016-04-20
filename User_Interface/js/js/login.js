var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var Router = require('react-router');

var Login = React.createClass({

  getInitialState: function () {
    return {  
      username: null,
      password: null
    }
  },

  isEmpty: function (value) {
    return !_.isEmpty(value);
  },

  handleUserNameInput: function(event) {
  	this.setState({
      username: event.target.value
    });
  },

  handleLogin: function(event){
  	event.preventDefault();

  	var canProceed = !_.isEmpty(this.state.username)
  	&& !_.isEmpty(this.state.password);

  	if(canProceed){
  		DynoCloud.login(this.state.username, this.state.password)
  		.catch(function(err) {
        console.log(“Error logging in”, err);
      });
  	}
  },

  render: function(){
  	return (
  		<form role="form" name="LoginCredentials" onSubmit={this.handleLogin}.bind(this)>
          <div class="form-group">
            <label class="control-label" for="UserEmail">Email address</label>
            <TextInput 
              className="form-control" 
              type="text" 
              ref="username"
              placeholder = "Enter username"
              validate={this.isEmpty}
              value={this.state.username}
              onChange={this.handleUserNameInput} 
              emptyMessage="Username cannot be empty."/>
          </div>
          <div class="form-group">
            <label class="control-label" for="UserPassword">Password</label>
            <input class="form-control" id="UserPassword" placeholder="Password" type="password" name="password">
          </div>
          <button type="submit" class="btn btn-success" value="Login">Log In</button>
    	</form>
  	);
  }
});

ReactDOM.render(<Login/>, document.getElementById('login-form'))