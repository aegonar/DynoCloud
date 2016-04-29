var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('../components/text-input.js');
var _ = require('underscore');



var EditAlertsSettings = React.createClass({
	getInitialState: function () {
    return {
      onScreen: false,
      email: false,
      phone: false,
      threshold: "0",
      retries: "0",
    }
  },

  isEmpty: function (value) {
    return !_.isEmpty(value);
  },

  validateNumber: function (event) {
    var regx = /^[0-9]*$/;
    return regx.test(event);
  },

  handleOnScreenChange: function(event){
    this.setState({
      onScreen: event.target.checked
    });
  },

  handleEmailChange: function(event){
    this.setState({
      email: event.target.checked
    });
  },

  handlePhoneChange: function(event){
    this.setState({
      phone: event.target.checked
    });
  },

  handleThresholdInput: function(event){
    this.setState({
      threshold: event.target.value
    });
  },

  handleRetriesInput: function (event) {
    this.setState({
      retries: event.target.value
    });
  },

  componentDidMount: function() {
    jQuery.ajax({
        url: 'http://dynocare.xyz/api/alerts/settings',
        dataType: 'json',
        beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
        success: this.successHandler
    });
  },

  successHandler: function(data) {
    this.setState({
      retries: data.retries,
      threshold: data.threshold,
  		onScreen: data.onScreen,
  		phone: data.phone,
  		email: data.email,
    });
    this.forceUpdate();
  },

  handleAlertsSettingsChange: function (e) {
    e.preventDefault();

    var canProceed = this.validateNumber(this.state.retries) 
        && this.validateNumber(this.state.threshold)

    if(canProceed) {
      var alertsData = {
        retries: this.state.retries,
        threshold: this.state.threshold,
        onScreen: this.state.onScreen,
        phone: this.state.phone,
        email: data.email,
      }

      var url = 'http://dynocare.xyz/api/alerts/settings';

      jQuery.ajax({
        url: url,
        dataType: 'json',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify( regData ),

        beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
          success: function(data){
            alert('Successfully updated alert settings.');
            window.location.reload();
          }
      });
    } 
  },

  render: function() {
      return (
        <form id="user-form" ref="userForm" role="form" onSubmit={this.handleAlertsSettingsChange} method="PUT">

          <div className="form-group">
            <div className="checkbox">
                <label>
                  <input type="checkbox" checked={this.state.onScreen} onChange={this.handleOnScreenChange}/>On-Screen
                </label>
              </div>
          </div>

          <div className="form-group">
            <div className="checkbox">
                <label>
                  <input type="checkbox" checked={this.state.email} onChange={this.handleEmailChange}/> Email
                </label>
              </div>
          </div>

          <div className="form-group">
            <div className="checkbox">
                <label>
                  <input type="checkbox" checked={this.state.phone} onChange={this.handlePhoneChange}/>Phone (text)
                </label>
              </div>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="threshold"
              placeholder = "Threshold #"
              validate={this.isEmpty}
              value={this.state.threshold}
              onChange={this.handleThresholdInput} 
              emptyMessage="Threshold cannot be empty."
              errorMessage="Threshold must be a number."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="retries"
              placeholder = "Retries #"
              validate={this.isEmpty}
              value={this.state.retries}
              onChange={this.handleRetriesInput} 
              emptyMessage="Retries cannot be empty."
              errorMessage="Retries must be a number."/>
          </div>

          
          <div className="modal-footer">
            <button ref="cancel" className="btn btn-default" data-dismiss="modal">Cancel</button>
            <button id="register" ref="submit" className="btn btn-primary" type="submit"> Save Changes</button>
          </div>
        </form>
      );
  }    
});

ReactDOM.render(<EditAlertsSettings/>,document.getElementById('edit-alerts-settings'))