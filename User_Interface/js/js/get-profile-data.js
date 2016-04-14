// load in JSON data from file
var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var Select = require('./components/profiles.js');

var OPTIONS = require('./data/default-profiles.js');
var data = require('./data/profiles.json');

var GetProfilesData= React.createClass({
  
  getInitialState: function(){
    return {
      petprofile: null,
      data: null,
      options: {OPTIONS}
    };
  },

  updateValue: function(newValue) {
    this.setState({
      petprofile: newValue
    });
  },
    
  componentDidMount: function() {
    this.setState({
      data: this.state.data
      });   
   }, 

  render: function() {
    return (
        <div>   
          <label>Select Pet Profile</label>
          <Select
            className="form-control"
            value={this.state.petprofile}
            onChange={this.updateValue}/>
            <div>
            <ul>
              {this.state.options.map(function(petprofile) {
                return <ListItemWrapper key={petprofile.id} data={petprofile}/>;
              })}
            </ul>
          </div>
        </div>
    );
  }
});

var ListItemWrapper = React.createClass({
  render: function() {
      return (
        <br> Day:
        <li>Time: {this.state.petprofile.day.time}</li>
        <li>Temperature Set Point: {this.state.petprofile.day.temperature}</li>
        <li>Temperature Threshold: {this.state.petprofile.day.temperatureThreshold}</li>
        <li>Humidity Set Point: {this.state.petprofile.day.humidity}</li>
        <li>Humidity Threshold: {this.state.petprofile.day.humidityTHreshold}</li>
        <br>Night:
        <li>Time: {this.state.petprofile.night.time}</li>
        <li>Temperature Set Point: {this.state.petprofile.night.temperature}</li>
        <li>Temperature Threshold: {this.state.petprofile.night.temperatureThreshold}</li>
        <li>Humidity Set Point: {this.state.petprofile.night.humidity}</li>
        <li>Humidity Threshold: {this.state.petprofile.night.humidityThreshold}</li>
        );
    }
});

ReactDOM.render(<GetProfilesData/>, document.getElementById('get-profiles-data'))