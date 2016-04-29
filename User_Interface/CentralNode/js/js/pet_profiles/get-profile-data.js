var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('../components/text-input.js');
var _ = require('underscore');
var RadioGroup = require('react-radio-group');

var GetProfilesData = React.createClass({
    getInitialState: function() {
        return {
            options: [],
            data:[],
            selected: {},

            profilename: "",
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
    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://192.168.0.200/node_api/profiles',
            dataType: 'json',
            
            success: this.successHandler
        })
    },
    
    successHandler: function(data) {
        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            this.state.options.push(
                <option key={option.petProfileID} value={option.value}>{option.petProfileID}</option>
            );
            this.state.data.push(option);
        }

        this.setState({
            selected: this.state.data[0],
            profilename: data[0].petProfileID,
            dayTime: data[0].dayTime.toString(),
            temperatureSetPointDay: data[0].day_Temperature_SP.toString(),
            temperatureThreshold: data[0].temperature_TH.toString(),
            humiditySetPointDay: data[0].day_Humidity_SP.toString(),
            humidityThreshold: data[0].humidity_TH.toString(),
            nightTime: data[0].nightTime.toString(),
            temperatureSetPointNight: data[0].night_Temperature_SP.toString(),
            humiditySetPointNight: data[0].night_Humidity_SP.toString(),
        });
        this.forceUpdate();
    },

    handleChange: function(event) {
        for(var i = 0; i < this.state.data.length; i++){
          var option = this.state.data[i];
          if(event.target.value == option.petProfileID){
            this.setState({
                selected: option,
                profilename: option.petProfileID,
                dayTime: option.dayTime,
                temperatureSetPointDay: option.day_Temperature_SP,
                humiditySetPointDay: option.day_Humidity_SP,
                nightTime: option.nightTime,
                temperatureSetPointNight: option.night_Temperature_SP,
                humiditySetPointNight: option.night_Humidity_SP,
                temperatureThreshold: option.temperature_TH,
                humidityThreshold: option.humidity_TH,
            });
          }
        }
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

          var url = 'http://d192.168.0.200/node_api/profiles/' + this.state.profilename;

          jQuery.ajax({
            url: url,
            dataType: 'json',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify( profData ),
            complete: this.handleEditSuccess
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

    handleEditSuccess: function(data){
        jQuery(document.getElementById('editProfile')).modal('toggle');
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

    handleDeleteProfile: function (e) {
       e.preventDefault();

      var url = 'http://192.168.0.200/node_api/profiles/' + this.state.profilename;

      jQuery.ajax({
        url: url,
        type: 'DELETE',

        success: function(response){
            alert('Profile deleted.');
            window.location.reload();
        },
        error: function(xhr, ajaxOptions, thrownError) {
            if(xhr.status == 500){
                alert("Pet profile in use on a module. Please change module settings before trying to delete.");
            }
            else if(xhr.status == 404){
                alert("Profile does not exists.");
            }
        }
      }); 
    },

    render: function() {
        return (
          <div>
            <div className="form-group">
            <span>
              <button type="button" className="btn btn-warning btn-circle" data-toggle="modal" data-target="#editProfile">
                <i className="fa fa-edit"></i>
              </button>
              <button type="button" className="btn btn-danger btn-circle" data-toggle="modal" data-target="#removeProfile">
                <i className="fa fa-trash"></i>
              </button>
            </span>
            </div>
            <div div className="form-group" {...this.props}>
                <select onChange={this.handleChange}>
                    {this.state.options}
                </select>
            </div>
            <div>
              <ul>
                <li>Temperature Threshold: {this.state.selected.temperature_TH}</li>
                <li>Humidity Threshold: {this.state.selected.humidity_TH}</li>
                <h4>Day:</h4>
                <li>Time: {this.state.selected.dayTime}</li>
                <li>Temperature Set Point: {this.state.selected.day_Temperature_SP}</li>
                <li>Humidity Set Point: {this.state.selected.day_Humidity_SP}</li>
                <h4>Night:</h4>
                <li>Time: {this.state.selected.nightTime}</li>
                <li>Temperature Set Point: {this.state.selected.night_Temperature_SP}</li>
                <li>Humidity Set Point: {this.state.selected.night_Humidity_SP}</li>
              </ul>
            </div>

            <div id="editProfile" className="modal fade" role="dialog">
                <div className="modal-dialog">
                  <div className="modal-content">
                    <div className="modal-header">
                      <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                      <h4 className="modal-title">Edit Pet Profile</h4>
                    </div>
                    <div className="modal-body">
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
                    </div>
                  </div>
                </div>
              </div>

              <div className="modal fade" id="removeProfile" role="dialog">
                <div className="modal-dialog">
                  <div className="modal-content">
                    <div className="modal-header">
                      <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                      <h4 className="modal-title">Confirmation</h4>
                    </div>
                    <div className="modal-body">
                      <form role="form" onSubmit={this.handleDeleteProfile} method="DELETE">
                        <p> Are you sure you want to delete the pet profile {this.state.profilename}?</p>      

                        <div className="modal-footer">
                          <button className="btn btn-default" data-dismiss="modal">Cancel</button>
                          <button className="btn btn-primary" type="submit">Confirm</button>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
          </div>
        );
    }
});

ReactDOM.render(<GetProfilesData/>, document.getElementById('get-profiles-data'))