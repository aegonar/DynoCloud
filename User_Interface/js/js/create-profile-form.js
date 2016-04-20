/*

validate fields
write data
redirect to profiles.html
add picture option

*/

var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var Router = require('react-router');
var TextInput = require('./components/text-input.js');

var CreateProfile = React.createClass({

  getInitialState: function () {
    return {
      profilename: null,
      dayTime: null,
      temperatureSetPointDay: null,
      temperatureThreshold: null,
      humiditySetPointDay: null,
      humidityThreshold: null,
      nightTime: null,
      temperatureSetPointNight: null,
      humiditySetPointNight: null,
    }
  },

  isEmpty: function (value) {
    return !_.isEmpty(value);
  },

  validateTemperature: function (event) {
    return null;
  },

  validateHumidity: function(event) {
    return null;
  },

  validateTemperatureThreshold: function(event) {
    return null;
  },

  validateHumidityThreshold: function(event) {
    return null;
  },

  handleProfileNameInput: function(event){
    this.setState({
      profilename: event.target.value
    });
  },

  handleDayTimeInput: function(event){
    this.setState({
      dayTime: event.target.value
    });
  },

  handleTempSetPointDayInput: function(event){
    this.setState({
      temperatureSetPointDay: event.target.value
    });
  },

  handleTempThresholdInput: function(event){
    this.setState({
      temperatureThreshold: event.target.value
    });
  },

  handleHumSetPointDayInput: function(event){
    this.setState({
      humiditySetPointDay: event.target.value
    });
  },

  handleHumThresholdInput: function(event){
    this.setState({
      humidityThreshold: event.target.value
    });
  },

  handleNightTimeInput: function(event){
    this.setState({
      nightTime: event.target.value
    });
  },

  handleTempSetPointNightInput: function(event){
    this.setState({
      temperatureSetPointNight: event.target.value
    });
  },

  handleHumSetPointNightInput: function(event){
    this.setState({
      humiditySetPointNight: event.target.value
    });
  },


  handleCreateProfile: function (e) {
    e.preventDefault();
      var canProceed = !_.isEmpty(this.state.temperatureSetPointDay) 
        && !_.isEmpty(this.state.temperatureSetPointNight)
        && !_.isEmpty(this.state.temperatureThreshold)
        && !_.isEmpty(this.state.humidityThreshold)
        && !_.isEmpty(this.state.humiditySetPointDay)
        && !_.isEmpty(this.state.humiditySetPointNight)
        && !_.isEmpty(this.state.profilename);
      
/*    var canProceed = this.validateTemperature(this.state.temperatureSetPointDay) 
        && this.validateTemperature(this.state.temperatureSetPointNight)
        && this.validateTemperatureThreshold(this.state.temperatureThresholdDay)
        && this.validateTemperatureThreshold(this.state.temperatureThresholdNight)
        && this.validateHumidityThreshold(this.state.humidityThresholdDay)
        && this.validateHumidityThreshold(this.state.humidityThresholdNight)
        && this.validateHumidity(this.state.humiditySetPointDay)
        && this.validateHumidity(this.state.humiditySetPointNight); */

    if(canProceed) {
      var profData = {        
        name: this.state.profilename,
        //dayTime: this.state.dayTime,
        day_Temperature_SP: this.state.temperatureSetPointDay,
        day_Humidity_SP: this.state.humiditySetPointDay,
        //nightTime: this.state.nightTime,
        night_Temperature_SP: this.state.temperatureSetPointNight,
        night_Humidity_SP: this.state.humiditySetPointNight,
        humidity_TH: this.state.humidityThreshold,
        temperature_TH: this.state.temperatureThreshold
      }

      var url = 'http://dynocare.xyz/api/profiles';
      mixins: [Router.Navigation],

      jQuery.ajax({
        url: url,
        dataType: 'json',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify( profData ),
        success: function(data, textStatus, jqXHR){
          //Close Modal Popup
          //Clear fields
          alert('Profile added.');
          self.transitionTo('profiles.html');
        },
        error: function(jqXHR, textStatus, errorThrown){
          console.error(url, status, errorThrown.toString());
        }.bind(this)
      });
    } 
    else {
      this.refs.profilename.isValid();
      this.refs.dayTime.isValid();
      this.refs.temperatureSetPointDay.isValid();
      this.refs.temperatureThreshold.isValid();
      this.refs.humiditySetPointDay.isValid();
      this.refs.humidityThreshold.isValid();
      this.refs.nightTime.isValid();
      this.refs.temperatureSetPointNight.isValid();
      this.refs.humiditySetPointNight.isValid();
    }
  },

  render: function() {
      return (
        <form role="form" onSubmit={this.handleCreateProfile} method="POST">

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="profilename"
              placeholder = "Profile Name *"
              validate={this.isEmpty}
              value={this.state.profilename}
              onChange={this.handleProfileNameInput} 
              emptyMessage="Profile name cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="time" 
              ref="dayTime"
              validate={this.isEmpty}
              value={this.state.dayTime}
              onChange={this.handleDayTimeInput} 
              emptyMessage="Day time cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="temperatureSetPointDay"
              placeholder = "Temperature Set Point *"
              validate={this.isEmpty}
              value={this.state.temperatureSetPointDay}
              onChange={this.handleTempSetPointDayInput} 
              emptyMessage="Temperature Set Point cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="humiditySetPointDay"
              placeholder = "Humidity Set Point (%) *"
              validate={this.isEmpty}
              value={this.state.humiditySetPointDay}
              onChange={this.handleHumSetPointDayInput} 
              emptyMessage="Humidity Set Point cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="time" 
              ref="nightTime"
              validate={this.isEmpty}
              value={this.state.dayTime}
              onChange={this.handleDayTimeInput} 
              emptyMessage="Day time cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="temperatureSetPointNight"
              placeholder = "Temperature Set Point *"
              validate={this.isEmpty}
              value={this.state.temperatureSetPointNight}
              onChange={this.handleTempSetPointNightInput} 
              emptyMessage="Temperature Set Point cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="humiditySetPointNight"
              placeholder = "Humidity Set Point (%) *"
              validate={this.isEmpty}
              value={this.state.humiditySetPointNight}
              onChange={this.handleHumSetPointNightInput} 
              emptyMessage="Humidity Set Point cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput
              className="form-control" 
              type="text" 
              ref="humidityThreshold"
              placeholder = "Humidity Threshold (%) *"
              validate={this.isEmpty}
              value={this.state.humidityThreshold}
              onChange={this.handleHumThresholdInput} 
              emptyMessage="Humidity Threshold cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput
              className="form-control" 
              type="text" 
              ref="temperatureThreshold"
              placeholder = "Temperature Threshold (%) *"
              validate={this.isEmpty}
              value={this.state.temperatureThreshold}
              onChange={this.handleTempThresholdInput} 
              emptyMessage="Temperature Threshold cannot be empty."/>
          </div>

          <div className="form-group">
            <label>* Required fields.</label>
          </div>        

          <div className="modal-footer">
            <button className="btn btn-default" data-dismiss="modal">Cancel</button>
            <button className="btn btn-primary" type="submit">Create Profile</button>
          </div>
        </form>
      );
    }    
});
      
ReactDOM.render(<CreateProfile/>, document.getElementById('create-profile-form'))