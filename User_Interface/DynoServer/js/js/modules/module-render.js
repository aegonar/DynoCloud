var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('../components/text-input.js');
var RadioGroup = require('react-radio-group');
var _ = require('underscore');

var GetModuleData = React.createClass({

    componentDidMount: function() {
        this.setupAjax();
        this.setState({
          idToken: this.getIdToken()
        });
    },

    setupAjax: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/module',
            dataType: 'json',
          beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
          success: this.loadModuleData
        });
    },

    getIdToken: function() {
        return localStorage.getItem('token');;
    },

    getInitialState: function() {
        return {
            data: [],
            module: {},
            enclosureNodeID: "",
            centralNodeID: "",

            modulename: "",
            petprofileID: "",
            optionalLoad: "0",
            options: [],
            profile_data:[],
            selected: {},

            HUM_OR: "0",
            HEAT_OR: "0",
            UV_OR: "0",
            OPTIONAL_OR: "0",
            HUM_STATUS: "0",
            HEAT_STATUS: "0",
            UV_STATUS: "0",
            OPTIONAL_STATUS: "0"
        }
    },

    loadModuleData: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/module',
            dataType: 'json',
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
            success: this.handleSuccess,
            complete: this.getProfileData,
        });
    },

    handleSuccess: function(data){
        this.setState({
            data: data,
            modules: [],
        });
        this.getModules(data);
    },

    getModules: function(data){
        for(var i = 0; i < data.length; i++){
            this.state.modules.push(
                <div key={data[i].enclosureNodeID}>
                    <div className="col-lg-3 col-md-6">
                    <div className="panel panel-default text-center">
                        <div className="panel-heading">
                            <div className="row">
                                <div className="col-xs-9 text-left col-md-12">
                                    <span className="pull-right">
                                        <button type="button" className="btn btn-info btn-circle" data-toggle="modal" onClick={this.editModuleModal.bind(this,data[i])} data-target="#editModule">
                                            <i className="fa fa-edit"></i>
                                        </button>                                    
                                    </span>
                                    <div className="huge">{data[i].name}</div>
                                    <div>Pet Profile: {data[i].petProfileID}</div>
                                    <div> Optional Load: {data[i].OPTIONAL_LOAD}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            
            );
            this.forceUpdate();
        }
    },

    editModuleModal: function(value){
        this.setState({
            module: value,
            enclosureNodeID: value.enclosureNodeID,
            centralNodeID: value.centralNodeID,
            petprofileID: value.petProfileID,
            modulename: value.name,
            optionalLoad: parseInt(value.OPTIONAL_LOAD),
        }, 
            function(){
                this.setState({
                    module: this.state.module,
                    enclosureNodeID: this.state.enclosureNodeID,
                    centralNodeID: this.state.centralNodeID,
                    petprofileID: this.state.petProfileID,
                    modulename: this.state.modulename,
                    optionalLoad: parseInt(this.state.optionalLoad),
                });
            }
        );

        this.requestOverrideData(value);
    },

    requestOverrideData: function(value){
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/overview/' + value.centralNodeID + '/' + value.enclosureNodeID,
            dataType: 'json',
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
            success: this.successOverrideHandler
        });
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

              complete: this.handleModuleEdit
          });
        } 
    },

    handleModuleEdit: function(){
        jQuery(document.getElementById('editModule')).modal('toggle');
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

    handleOverrideSubmit: function (e) {
       e.preventDefault();
       var hum = "0";
       var heat = "0";
       var uv = "0";
       var opt = "0";

       if(this.state.HUM_OR){
            hum = "1";
       }
       if(this.state.HEAT_OR){
            heat = "1";
       }
       if(this.state.UV_OR){
            uv = "1";
       }
       if(this.state.OPTIONAL_OR){
            opt = "1";
       }

        var overrideData = {
            HUM_OR: hum,
            HEAT_OR: heat,
            UV_OR: uv,
            OPTIONAL_OR: opt,

            HUM_STATUS: this.state.HUM_STATUS,
            HEAT_STATUS: this.state.HEAT_STATUS,
            UV_STATUS: this.state.UV_STATUS,
            OPTIONAL_STATUS: this.state.OPTIONAL_STATUS
        }

        jQuery.ajax({
            url: 'http://dynocare.xyz/api/override/' + this.state.centralNodeID + '/' + this.state.enclosureNodeID,
            dataType: 'json',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify( overrideData ),
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
          success: this.handleSuccessOverride
        });
    },

    handleSuccessOverride: function(){
        alert('Override Added');
        window.location.reload();
    },

    successOverrideHandler: function(data) {
        this.setState({
            centralNodeID: data[0].centralNodeID,
            enclosureNodeID: data[0].enclosureNodeID,
            HUM_OR: data[0].HUM_OR,
            HEAT_OR: data[0].HEAT_OR,
            UV_OR: data[0].UV_OR,
            OPTIONAL_OR: data[0].OPTIONAL_OR,
            
            HUM_STATUS: data[0].HUM_STATUS,
            HEAT_STATUS: data[0].HEAT_STATUS,
            UV_STATUS: data[0].UV_STATUS,
            OPTIONAL_STATUS: data[0].OPTIONAL_STATUS
        });
        this.forceUpdate();
    },

    handleHumidifierEnable: function(event){
        this.setState({
                HUM_OR: event.target.checked
            },
            function(){
                this.setState({
                    HUM_OR: this.state.HUM_OR
                });
            }
        );
    },

    handleHeatLampEnable: function(event){
        this.setState({
                HEAT_OR: event.target.checked
            },
            function(){
                this.setState({
                    HEAT_OR: this.state.HEAT_OR
                });
            }
        );
    },

    handleUltravioletEnable: function(event){
        this.setState({
                UV_OR: event.target.checked
            },
            function(){
                this.setState({
                    UV_OR: this.state.UV_OR
                });
            }
        );
    },

    handleOptionalEnable: function(event){
        this.setState({
                OPTIONAL_OR: event.target.checked
            },
            function(){
                this.setState({
                    OPTIONAL_OR: this.state.OPTIONAL_OR
                });
            }
        );
    },

    handleHumidifierStatus: function(value){
        this.setState({
            HUM_STATUS: value
        },
            function(){
                this.setState({
                    HUM_STATUS: this.state.HUM_STATUS
                });
            }
        );
    },

    handleHeatLampStatus: function(value){
        this.setState({
            HEAT_STATUS: value
        },
            function(){
                this.setState({
                    HEAT_STATUS: this.state.HEAT_STATUS
                });
            }
        );
    },

    handleUltravioletStatus: function(value){
        console.log(value);
        this.setState({
            UV_STATUS: value
        },
            function(){
                this.setState({
                    UV_STATUS: this.state.UV_STATUS
                });
            }
        );
    },

    handleOptionalStatus: function(value){
        this.setState({
            OPTIONAL_STATUS: value
        },
            function(){
                this.setState({
                    OPTIONAL_STATUS: this.state.OPTIONAL_STATUS
                });
            }
        );
    },
    
    render: function() {
        return (
            <div>
                <div {...this.props}>
                {
                    this.state.modules
                }
                </div>

                <div id="editModule" className="modal fade" role="dialog">
                <div className="modal-dialog">
                    <div className="modal-dialog">

                        <div className="modal-content">

                            <div className="modal-header">
                                <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h4 className="modal-title">Edit Module</h4>
                            </div>

                            <div className="modal-body">
                                <form role="form" onSubmit={this.handleModuleEditSubmit} method="PUT">
                                    <div className="form-group">
                                      <label><h4>Module Name</h4></label>
                                      <TextInput 
                                        className="form-control" 
                                        type="text" 
                                        validate={this.isEmpty}
                                        value={this.state.module.name}
                                        onChange={this.handleModuleNameInput}
                                        emptyMessage="Module name cannot be empty."/>
                                    </div>

                                    <div className="form-group">
                                        <label><h4>Select Pet Profile</h4></label>
                                        <div {...this.props}>
                                            <select onChange={this.handleProfileChange} value={this.state.module.petProfileID}>
                                                {this.state.options}
                                            </select>
                                        </div>
                                    </div>

                                    <div className="form-group">
                                        <label><h4>Choose Optional Load</h4></label>
                                        <RadioGroup name="optional_load" selectedValue={this.state.module.OPTIONAL_LOAD+""} onChange={this.handleOptLoadChange}>
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
                                        <div>
                                            <div className="form-group">
                                                <label>
                                                    <input type="checkbox"
                                                      name="humidifierEnable"
                                                      checked={this.state.HUM_OR}
                                                      onChange={this.handleHumidifierEnable}
                                                      value={this.state.HUM_OR} />
                                                        Override Humidifier
                                                </label>
                                                <RadioGroup id="humidifier" name="humidifier" selectedValue={this.state.HUM_STATUS+""} onChange={this.handleHumidifierStatus}>
                                                    {Radio => (
                                                        <div>
                                                          <Radio value="1" /> on <br/>
                                                          <Radio value="0" /> off <br/> 
                                                        </div>
                                                    )}
                                                </RadioGroup>
                                            </div>

                                            <div className="form-group">
                                                <label>
                                                    <input type="checkbox"
                                                      name="heatpadEnable"
                                                      checked={this.state.HEAT_OR}
                                                      onChange={this.handleHeatLampEnable}
                                                      value={this.state.HEAT_OR} />
                                                        Override Heat Pad
                                                </label>
                                                <RadioGroup id="heat_lamp" name="heat_lamp" selectedValue={this.state.HEAT_STATUS+""} onChange={this.handleHeatLampStatus}>
                                                    {Radio => (
                                                        <div>
                                                          <Radio value="1" /> on <br/>
                                                          <Radio value="0" /> off <br/> 
                                                        </div>
                                                    )}
                                                </RadioGroup>
                                            </div>

                                            <div className="form-group">
                                                <label>
                                                    <input type="checkbox"
                                                      name="uvEnable"
                                                      checked={this.state.UV_OR}
                                                      onChange={this.handleUltravioletEnable}
                                                      value={this.state.UV_OR} />
                                                        Override Ultraviolet (UV)
                                                </label>
                                                <RadioGroup id="ultraviolet" name="ultraviolet" selectedValue={this.state.UV_STATUS+""} onChange={this.handleUltravioletStatus}>
                                                    {Radio => (
                                                        <div>
                                                          <Radio value="1" /> on <br/>
                                                          <Radio value="0" /> off <br/> 
                                                        </div>
                                                    )}
                                                </RadioGroup>
                                            </div>

                                            <div className="form-group">
                                                <label>
                                                    <input type="checkbox"
                                                      name="optionalEnable"
                                                      checked={this.state.OPTIONAL_OR}
                                                      onChange={this.handleOptionalEnable}
                                                      value={this.state.OPTIONAL_OR} />
                                                        Override OPTIONAL
                                                </label>
                                                <RadioGroup id="optional" name="optional" selectedValue={this.state.OPTIONAL_STATUS+""} onChange={this.handleOptionalStatus}>
                                                    {Radio => (
                                                        <div>
                                                          <Radio value="1" /> on <br/>
                                                          <Radio value="0" /> off <br/> 
                                                        </div>
                                                    )}
                                                </RadioGroup>
                                            </div>

                                            <div>
                                                <button className="btn btn-xs btn-default" id="changes" type="submit" onClick={this.handleOverrideSubmit} method="POST">Add Override</button>
                                            </div> 
                                        </div>

                                    </div>
                                </form> 
                            </div>

                            <div className="modal-footer">
                              <button className="btn btn-default" data-dismiss="modal">Cancel</button>
                              <button className="btn btn-primary" type="submit">Save Changes</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        );
    }
});

ReactDOM.render(<GetModuleData/>, document.getElementById('module'))