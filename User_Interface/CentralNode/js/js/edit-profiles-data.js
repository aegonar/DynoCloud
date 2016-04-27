var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('./components/text-input.js');
var _ = require('underscore');
var RadioGroup = require('react-radio-group');

var EditProfile = React.createClass({
  
	getInitialState: function(){
		return {
			profilename: "",
      		dayTime: null,
      		temperatureSetPointDay: null,
      		temperatureThreshold: null,
      		humiditySetPointDay: null,
      		humidityThreshold: null,
      		nightTime: null,
      		temperatureSetPointNight: null,
      		humiditySetPointNight: null,
		};
	},

	isEmpty: function (value) {
	    return !_.isEmpty(value);
	},

  	handleEditProfile: function (e) {
	   e.preventDefault();

	    var canProceed = !_.isEmpty(this.state.profilename)
	        && !_.isEmpty(this.state.temperatureSetPointDay)
	        && !_.isEmpty(this.state.humiditySetPointDay)
	        && !_.isEmpty(this.state.temperatureSetPointNight)
	        && !_.isEmpty(this.state.humiditySetPointNight)
	        && !_.isEmpty(this.state.temperatureThreshold)
	        && !_.isEmpty(this.state.humidityThreshold);

	    if(canProceed) {

	      var profData = {
	        petProfileID: this.state.profilename,
	        dayTime: this.state.dayTime,
	        day_Temperature_SP: this.state.temperatureSetPointDay,
	        day_Humidity_SP: this.state.humiditySetPointDay,
	        nightTime: this.state.nightTime,
	        night_Temperature_SP: this.state.temperatureSetPointNight,
	        night_Humidity_SP: this.state.humiditySetPointNight,
	        temperature_TH: this.state.temperatureThreshold,
	        humidity_TH: this.state.humidityThreshold,
	      };

	      var url = 'http://localhost/node_api/profiles/' + this.state.profilename;

	      jQuery.ajax({
	        url: url,
	        dataType: 'json',
	        type: 'PUT',
	        contentType: 'application/json',
	        data: JSON.stringify( profData ),
	      });
	    } 
	    else {
	      this.refs.profilename.isValid();
	      this.refs.temperatureSetPointDay.isValid();
	      this.refs.humiditySetPointDay.isValid();
	      this.refs.temperatureSetPointNight.isValid();
	      this.refs.humiditySetPointNight.isValid();
	      this.refs.humidityThreshold.isValid();
	      this.refs.temperatureThreshold.isValid();
	    }
	},

	componentDidMount: function() {
        jQuery.ajax({
            url: 'http://localhost/node_api/profiles',
            dataType: 'json',
            
            success: this.successHandler
        });
    },

    successHandler: function(data) {
        this.setState({
        	profilename: data[0].petProfileID,
      		dayTime: data[0].dayTime.toString(),
      		temperatureSetPointDay: data[0].day_Temperature_SP.toString(),
      		temperatureThreshold: data[0].temperature_TH.toString(),
      		humiditySetPointDay: data[0].day_Humidity_SP.toString(),
      		humidityThreshold: data[0].humidity_TH.toString(),
      		nightTime: date[0].nightTime.toString(),
      		temperatureSetPointNight: data[0].night_Temperature_SP.toString(),
      		humiditySetPointNight: data[0].night_Humidity_SP.toString(),
        });
        this.forceUpdate();
    },

    handleProfileNameEdit: function(event){
    	this.setState({
    		profilename: event.target.value
    	});
    },

    handleDayTimeEdit: function(event){
    	this.setState({
    		dayTime: event.target.value
    	});
    },

    handleNightTimeEdit: function(event){
    	this.setState({
    		nightTime: event.target.value
    	});
    },

    handleTempSetPointDayEdit: function(event){
    	this.setState({
    		temperatureSetPointDay: event.target.value
    	});
    },

    handleHumSetPointDayEdit: function(event){
    	this.setState({
    		humiditySetPointDay: event.target.value
    	});
    },

    handleTempSetPointNightEdit: function(event){
    	this.setState({
    		temperatureSetPointNight: event.target.value
    	});
    },

    handleHumSetPointNightEdit: function(event){
    	this.setState({
    		humiditySetPointNight: event.target.value
    	});
    },


    handleTempThresholdEdit: function(event){
    	this.setState({
    		temperatureThreshold: event.target.value
    	});
    },

    handleHumThresholdEdit: function(event){
    	this.setState({
    		humidityThreshold: event.target.value
    	});
    },

	render: function() {
	    return (
	      	<form role="form" onSubmit={this.handleEditProfile} method="PUT">
		        <div className="form-group">
		          <TextInput 
		            className="form-control" 
		            type="text" 
		            ref="profilename"
		            placeholder = "Profile Name *"
		            validate={this.isEmpty}
		            value={this.state.profilename}
		            onChange={this.handleProfileNameEdit} 
		            emptyMessage="Profile name cannot be empty."/>
		        </div>

		        <div className="form-group">
		          <TextInput 
		            className="form-control" 
		            type="text" 
		            ref="dayTime"
		            validate={this.isEmpty}
		            value={this.state.dayTime}
		            onChange={this.handleDayTimeEdit} 
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
		            onChange={this.handleTempSetPointDayEdit} 
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
		            onChange={this.handleHumSetPointDayEdit} 
		            emptyMessage="Humidity Set Point cannot be empty."/>
		        </div>

		        <div className="form-group">
		          <TextInput 
		            className="form-control" 
		            type="text" 
		            ref="nightTime"
		            validate={this.isEmpty}
		            value={this.state.nightTime}
		            onChange={this.handleNightTimeEdit} 
		            emptyMessage="Night time cannot be empty."/>
		        </div>

		        <div className="form-group">
		          <TextInput 
		            className="form-control" 
		            type="text" 
		            ref="temperatureSetPointNight"
		            placeholder = "Temperature Set Point *"
		            validate={this.isEmpty}
		            value={this.state.temperatureSetPointNight}
		            onChange={this.handleTempSetPointNightEdit} 
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
		            onChange={this.handleHumSetPointNightEdit} 
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
		            onChange={this.handleHumThresholdEdit} 
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
		            onChange={this.handleTempThresholdEdit} 
		            emptyMessage="Temperature Threshold cannot be empty."/>
		        </div>

		        <div className="form-group">
		          <label>* Required fields.</label>
		        </div>        

		        <div className="modal-footer">
		          <button className="btn btn-default" data-dismiss="modal">Cancel</button>
		          <button className="btn btn-primary" type="submit">Save Changes</button>
		        </div>
		      </form>
	    );
	}
});

ReactDOM.render(<EditProfile/>,document.getElementById('edit-profile'))