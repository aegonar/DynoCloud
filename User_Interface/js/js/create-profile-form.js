/*

validate fields
write data to JSON (then AJAX)
redirect to overview.html
add this form to Add Module Select Pet Profile (custom)
add picture option

*/

var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var TextInput = require('./components/text-input.js');

var CreateProfile = React.createClass({

  getInitialState: function () {
    return {
      profilename: null,
      dayTime: null,
      temperatureSetPointDay: null,
      temperatureThresholdDay: null,
      humiditySetPointDay: null,
      humidityThresholdDay: null,
      nightTime: null,
      temperatureSetPointNight: null,
      temperatureThresholdNight: null,
      humiditySetPointNight: null,
      humidityThresholdNight: null,
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

  handleTempThresholdDayInput: function(event){
    this.setState({
      temperatureThresholdDay: event.target.value
    });
  },

  handleHumSetPointDayInput: function(event){
    this.setState({
      humiditySetPointDay: event.target.value
    });
  },

  handleHumThresholdDayInput: function(event){
    this.setState({
      humidityThresholdDay: event.target.value
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

  handleTempThresholdNightInput: function(event){
    this.setState({
      temperatureThresholdNight: event.target.value
    });
  },

  handleHumSetPointNightInput: function(event){
    this.setState({
      humiditySetPointNight: event.target.value
    });
  },

  handleHumThresholdNightInput: function(event){
    this.setState({
      humidityThresholdNight: event.target.value
    });
  },


  saveAndContinue: function (e) {
    e.preventDefault();
      var canProceed = !_.isEmpty(this.state.temperatureSetPointDay) 
        && !_.isEmpty(this.state.temperatureSetPointNight)
        && !_.isEmpty(this.state.temperatureThresholdDay)
        && !_.isEmpty(this.state.temperatureThresholdNight)
        && !_.isEmpty(this.state.humidityThresholdDay)
        && !_.isEmpty(this.state.humidityThresholdNight)
        && !_.isEmpty(this.state.humiditySetPointDay)
        && !_.isEmpty(this.state.humiditySetPointNight);
      
/*    var canProceed = this.validateTemperature(this.state.temperatureSetPointDay) 
        && this.validateTemperature(this.state.temperatureSetPointNight)
        && this.validateTemperatureThreshold(this.state.temperatureThresholdDay)
        && this.validateTemperatureThreshold(this.state.temperatureThresholdNight)
        && this.validateHumidityThreshold(this.state.humidityThresholdDay)
        && this.validateHumidityThreshold(this.state.humidityThresholdNight)
        && this.validateHumidity(this.state.humiditySetPointDay)
        && this.validateHumidity(this.state.humiditySetPointNight); */

    if(canProceed) {
      var data = {        
        profilename: this.state.profilename,
        dayTime: this.state.dayTime,
        temperatureSetPointDay: this.state.temperatureSetPointDay,
        temperatureThresholdDay: this.state.temperatureThresholdDay,
        humiditySetPointDay: this.state.humiditySetPointDay,
        humidityThresholdDay: this.state.humidityThresholdDay,
        nightTime: this.state.nightTime,
        temperatureSetPointNight: this.state.temperatureSetPointNight,
        temperatureThresholdNight: this.state.temperatureThresholdNight,
        humiditySetPointNight: this.state.humiditySetPointNight,
        humidityThresholdNight: this.state.humidityThresholdNight
      }
      alert('New pet profile created.');
    } 
    else {
      this.refs.profilename.isValid();
      this.refs.dayTime.isValid();
      this.refs.temperatureSetPointDay.isValid();
      this.refs.temperatureThresholdDay.isValid();
      this.refs.humiditySetPointDay.isValid();
      this.refs.humidityThresholdDay.isValid();
      this.refs.nightTime.isValid();
      this.refs.temperatureSetPointNight.isValid();
      this.refs.temperatureThresholdNight.isValid();
      this.refs.humiditySetPointNight.isValid();
      this.refs.humidityThresholdNight.isValid();
    }
  },

  render: function() {
      return (
        <form role="form" onSubmit={this.saveAndContinue} method="POST">

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
              ref="temperatureThresholdDay"
              placeholder = "Temperature Threshold *"
              validate={this.isEmpty}
              value={this.state.temperatureThresholdDay}
              onChange={this.handleTempThresholdDayInput} 
              emptyMessage="Temperature Threshold cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="humiditySetPointDay"
              placeholder = "Humidity Set Point *"
              validate={this.isEmpty}
              value={this.state.humiditySetPointDay}
              onChange={this.handleHumSetPointDayInput} 
              emptyMessage="Humidity Set Point cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput
              className="form-control" 
              type="text" 
              ref="humidityThresholdDay"
              placeholder = "Humidity Threshold *"
              validate={this.isEmpty}
              value={this.state.humidityThresholdDay}
              onChange={this.handleHumThresholdDayInput} 
              emptyMessage="Humidity Threshold cannot be empty."/>
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
              ref="temperatureThresholdNight"
              placeholder = "Temperature Threshold *"
              validate={this.isEmpty}
              value={this.state.temperatureThresholdNight}
              onChange={this.handleTempThresholdNightInput} 
              emptyMessage="Temperature Threshold cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput 
              className="form-control" 
              type="text" 
              ref="humiditySetPointNight"
              placeholder = "Humidity Set Point *"
              validate={this.isEmpty}
              value={this.state.humiditySetPointNight}
              onChange={this.handleHumSetPointNightInput} 
              emptyMessage="Humidity Set Point cannot be empty."/>
          </div>

          <div className="form-group">
            <TextInput
              className="form-control" 
              type="text" 
              ref="humidityThresholdNight"
              placeholder = "Humidity Threshold *"
              validate={this.isEmpty}
              value={this.state.humidityThresholdNight}
              onChange={this.handleHumThresholdNightInput} 
              emptyMessage="Humidity Threshold cannot be empty."/>
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