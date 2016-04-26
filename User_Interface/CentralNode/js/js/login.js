var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var Router = require('react-router');
var TextInput = require('./components/text-input.js');

var Login = React.createClass({

  getInitialState: function () {
    return {  
      username: null,
      password: null,
      isLogged: null,
      display: []
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

  handlePasswordInput: function(event){
    this.setState({
      password: event.target.value
    });
  },

  handleLogin: function(event){
  	event.preventDefault();

  	var canProceed = !_.isEmpty(this.state.username)
  	&& !_.isEmpty(this.state.password);

  	if(canProceed){
  		var loginData = {
          username: this.state.username,
          password: this.state.password
        }

        var url = 'http://dynocare.xyz/node_api/login';

        jQuery.ajax({
          url: url,
          dataType: 'json',
          type: 'POST',
          contentType: 'application/json',
          data: JSON.stringify( loginData ),
          //500 y 200
        });
  	}
  },

  componentDidMount: function() {
    jQuery.ajax({
        url: 'http://dynocare.xyz/node_api/login',
        dataType: 'json',

        success: this.successHandler
    });
  },

  successHandler: function(data) {

    this.setState({
      isLogged: data.online
    });

    if(this.state.isLogged == 0){
      this.state.display.push(
        <form role="form"  onSubmit={this.handleLogin}>
          <div className="form-group">
            <label className="control-label">Username
            </label>
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
          <div className="form-group">
            <label className="control-label">Password
            </label>
            <TextInput 
              className="form-control" 
              type="password"
              ref="password"
              placeholder="Password"
              validate={this.isEmpty}
              value={this.state.password} 
              onChange={this.handlePasswordInput}
              emptyMessage="Password cannot be empty."/>
          </div>
          <button type="submit" className="btn btn-success" value="Login">Log In
          </button>
        </form>
      );
    }
    else{
      this.state.display.push(<label>temp</label>);
    }
  },

  render: function(){
  	return (
      <div>
        {this.state.display}
      </div>
  	);
  }
});

ReactDOM.render(<Login/>, document.getElementById('login-form'))