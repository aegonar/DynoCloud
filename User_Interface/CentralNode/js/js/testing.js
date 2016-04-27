var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('./components/text-input.js');
var RadioGroup = require('react-radio-group');
var _ = require('underscore');

var GetModuleEnclosure = React.createClass({
    getInitialState: function() {
        return {
            enclosureNodeID: "",
            data:{},

            modulename: null,
            petprofileID: "",
            optionalLoad: "0",
            options: [],
            profile_data:[],
            selected: {},
        }
    },

    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID,
            dataType: 'json',

            success: this.successHandler,
        })
    },

    successHandler: function(data){
        this.setState({
            data: data
        });
    },

    handleDeleteModule: function (e) {
       e.preventDefault();

      var url = 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID;

      jQuery.ajax({
        url: url,
        type: 'DELETE',

        success: function(response){
            alert('Module deleted.');
        },
        error: function(xhr, ajaxOptions, thrownError) {
            if(xhr.status == 500){
                alert("There was an error.");
            }
            else if(xhr.status == 404){
                alert("Module does not exists.");
            }
        }
      }); 
    },

    isEmpty: function (value) {
        return !_.isEmpty(value);
    },

    handleModuleEditSubmit: function (e) {
       e.preventDefault();

        var canProceed = !_.isEmpty(this.state.modulename);

        if(canProceed) {

          var modData = {
            name: this.state.modulename,
            petProfileID: this.state.petprofileID,
            OPTIONAL_LOAD: parseInt(this.state.optionalLoad),
          }

          var url = 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID;

          jQuery.ajax({
            url: url,
            dataType: 'json',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify( modData ),
          });
        } 
        else {
          this.refs.moduleName.isValid();
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
            url: 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID,
            dataType: 'json',

            success: this.successEditHandler,
            complete: this.getProfileData
        })
    },

    successEditHandler: function(data) {
        this.setState({
            enclosureNodeID: data.enclosureNodeID,
            petprofileID: data.petProfileID,
            modulename: data.name,
            optionalLoad: parseInt(data.OPTIONAL_LOAD),
            selected: data
        });
        this.forceUpdate();
    },

    getProfileData: function(){
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/profiles',
            dataType: 'json',
            
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

    render: function() {
        return (
            <div>
                <div className="col-lg-3 col-md-6">
                    <div className="panel panel-default text-center">
                        <div className="panel-heading">
                            <div className="row">
                                <div className="col-xs-9 text-left col-md-12">
                                    <span className="pull-right">
                                        <button type="button" className="btn btn-info btn-circle" data-toggle="modal" data-target="#editModule">
                                            <i className="fa fa-edit"></i>
                                        </button>
                                        <button type="button" className="btn btn-danger btn-circle" data-toggle="modal" data-target="#removeModule">
                                            <i className="fa fa-times"></i>
                                        </button>
                                    </span>
                                    <div className="huge">{this.state.data.name}</div>
                                    <div>Pet Profile: {this.state.data.petProfileID}</div>
                                    <div> Optional Load: {this.state.data.OPTIONAL_LOAD}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="editModule" className="modal fade" role="dialog">
                    <div className="modal-dialog">
                        <div className="modal-dialog">

                            <div className="modal-content">

                                <div className="modal-header">
                                    <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 className="modal-title">Delete Module</h4>
                                </div>

                                <div className="modal-body">
                                    <form role="form" onSubmit={this.handleModuleEditSubmit} method="PUT">
                                        <div className="form-group">
                                          <label><h4>Module Name</h4></label>
                                          <TextInput 
                                            className="form-control" 
                                            type="text" 
                                            ref="moduleName"
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
                                                      <Radio value="2" /> Heating Lamp <br/>
                                                      <Radio value="0" /> None 
                                                    </div>
                                                )}
                                            </RadioGroup>
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

                <div id="removeModule" className="modal fade" role="dialog">
                    <div className="modal-dialog">
                        <div className="modal-dialog">

                            <div className="modal-content">

                                <div className="modal-header">
                                    <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <h4 className="modal-title">Delete Module</h4>
                                </div>

                                <div className="modal-body">
                                    <form role="form" onSubmit={this.handleDeleteModule} method="DELETE">
                                        <p> Are you sure you want to delete this module?</p>      
                                  </form>
                                </div>

                                <div className="modal-footer">
                                  <button className="btn btn-default" data-dismiss="modal">Cancel</button>
                                  <button className="btn btn-primary" type="submit">Confirm</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
});

module.exports = GetModuleEnclosure;