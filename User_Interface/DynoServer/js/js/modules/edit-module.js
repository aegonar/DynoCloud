var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('../components/text-input.js');
var Override = require('../override/override.js');
var RadioGroup = require('react-radio-group');
var _ = require('underscore');

var EditModule = React.createClass({
    componentDidMount: function() {
        this.setupAjax();
        this.setState({
          idToken: this.getIdToken()
        });
    },

    setupAjax: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/module/' + this.state.centralNodeID + '/' + this.state.enclosureNodeID,
            dataType: 'json',
          beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
          success: this.handleEditModule
        });
    },

    getIdToken: function() {
        return localStorage.getItem('token');;
    },

	getInitialState: function() {
        return {
        	enclosureNodeID: this.props.enclosureNodeID,
            centralNodeID: this.props.centralNodeID,
        	modulename: null,
            petprofileID: "",
            optionalLoad: "0",
            options: [],
            profile_data:[],
            selected: {},
        }
    },

    isEmpty: function (value) {
        return !_.isEmpty(value);
    },

    handleModuleEditSubmit: function (event) {
       event.preventDefault();

        var canProceed = !_.isEmpty(this.state.modulename);

        if(canProceed) {

          var modData = {
            name: this.state.modulename,
            petProfileID: this.state.petprofileID,
            OPTIONAL_LOAD: parseInt(this.state.optionalLoad),
          };

          //var url = 'http://dynocare.xyz/api/module/' + this.state.centralNodeID + '/' + this.state.enclosureNodeID,

          jQuery.ajax({
            url: 'http://dynocare.xyz/api/module/' + this.state.centralNodeID + '/' + this.state.enclosureNodeID,
            dataType: 'json',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify( modData ),

            beforeSend: function(xhr) {
                if (localStorage.getItem('token')) {
                  xhr.setRequestHeader('Authorization',
                        'Bearer ' + localStorage.getItem('token'));
                }
              },

              success: function(){
                alert('Module modified.');
                window.location.replace('./modules.html');
              }
          });
        } 
    },

    handleModuleNameInput: function(event){
        this.setState({
            modulename: event.target.value
        });
    },

    handleOptLoadChange: function(value){
        this.setState({
            optionalLoad: value
        });
    },

    handleEditModule: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/module/' + this.state.centralNodeID + '/' + this.state.enclosureNodeID,
            dataType: 'json',
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },

            success: this.successEditHandler,
            complete: this.getProfileData
        })
    },

    successEditHandler: function(data) {
        this.setState({
            enclosureNodeID: data.enclosureNodeID,
            centralNodeID: data.centralNodeID,
            petprofileID: data.petProfileID,
            modulename: data.name,
            optionalLoad: parseInt(data.OPTIONAL_LOAD),
        });
        this.forceUpdate();
    },

    getProfileData: function(){
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/profiles',
            dataType: 'json',
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
            
            success: this.successHandleDataProfiles
        })
    },

    successHandleDataProfiles: function(data) {

        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            this.state.options.push(
                <option key={option.petProfileID} value={option.value}>{option.petProfileID}</option>
            );
            this.state.profile_data.push(option);
        }

        this.setState({
          selected: this.state.petprofileID,
        });
        this.forceUpdate();
    },

    handleProfileChange: function(event) {
        for(var i = 0; i < this.state.profile_data.length; i++){
            var option = this.state.profile_data[i];
            if(event.target.value == option.petProfileID){
                this.setState({
                    selected: option,
                    petprofileID: option.petProfileID,
                }, 
                    function(){
                        this.setState({
                            selected: this.state.selected,
                            petprofileID: this.state.petprofileID
                        });
                    }   
                );
            }
        }
    },

    componentWillReceiveProps: function (newProps) {    
        if(newProps.enclosureNodeID && newProps.centralNodeID) {
            this.setState({
              enclosureNodeID: newProps.enclosureNodeID,
              centralNodeID: newProps.centralNodeID
            });
        }   
    },

	render: function() {
        return (
        	<div id="editModule" className="modal fade" role="dialog">
                <div className="modal-dialog">
                    <div className="modal-dialog">

                        <div className="modal-content">

                            <div className="modal-header">
                                <button type="button" className="close" data-dismiss="modal" aria-hidden="true" onClick={this.clearProfilesOptions}>×</button>
                                <h4 className="modal-title">Delete Module</h4>
                            </div>

                            <div className="modal-body">
                                <form role="form" onSubmit={this.handleModuleEditSubmit} method="PUT">
                                    <div className="form-group">
                                      <label><h4>Module Name</h4></label>
                                      <TextInput 
                                        className="form-control" 
                                        type="text" 
                                        validate={this.isEmpty}
                                        value={this.state.modulename}
                                        onChange={this.handleModuleNameInput}
                                        emptyMessage="Module name cannot be empty."/>
                                    </div>

                                    <div className="form-group">
                                        <label><h4>Select Pet Profile</h4></label>
                                        <div {...this.props}>
                                            <select onChange={this.handleProfileChange} value={this.state.petprofileID}>
                                                {this.state.options}
                                            </select>
                                        </div>
                                    </div>

                                    <div className="form-group">
                                        <label><h4>Choose Optional Load</h4></label>
                                        <RadioGroup name="optional_load" selectedValue={this.state.optionalLoad} onChange={this.handleOptLoadChange}>
                                                {Radio => (
                                                <div>
                                                  <Radio value="1" /> Infrared (IR) <br/>
                                                  <Radio value="2" /> Heating Pad <br/>
                                                  <Radio value="0" /> None 
                                                </div>
                                            )}
                                        </RadioGroup>
                                    </div>

                                    <div className="form-group">
                                    	<Override centralNodeID={this.state.centralNodeID} enclosureNodeID={this.state.enclosureNodeID}/>
                                    </div>
                                </form> 
                            </div>

                            <div className="modal-footer">
                              <button className="btn btn-default" data-dismiss="modal" onClick={this.clearProfilesOptions}>Cancel</button>
                              <button className="btn btn-primary" type="submit" onClick={this.clearProfilesOptions}>Save Changes</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = EditModule;