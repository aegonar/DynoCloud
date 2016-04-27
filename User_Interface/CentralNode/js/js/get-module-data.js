var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');

/* Modal Requirements */
var Modal = require('react-modal');

/* Edit Modal Requirements */
var RadioGroup = require('react-radio-group');
//var Override = require('./override.js');
var TextInput = require('./components/text-input.js');

//var EditModule = require('./edit-module-data.js');

var GetModulesData = React.createClass({
    getInitialState: function() {
        return {
            modules: [],
            modules_data: [],

            enclosureNodeID: "",

            /* Edit Modal variables */
            isEditModalOpen: false, //Handling Modal Open/Close
            modulename: "",
            petprofileID: "",
            optionalLoad: "0",
            options: [],
            profile_data: [],
            profile_selected: {},

            /* Delete Modal Variables */
            isDeleteModalOpen: false,

        }
    },

    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module',
            dataType: 'json',
            
            success: this.successHandler
        })
    },

    successHandler: function(data) {
        var opt_load = "None";
        for (var i = 0; i < data.length; i++) {
            var module = data[i];
            if(module.OPTIONAL_LOAD == 1){
                opt_load = "Infrared";
            }
            else if(module.OPTIONAL_LOAD == 2){
                opt_load = "Heating Lamp";
            }
            this.state.modules_data.push(module);

            this.state.modules.push(
                <div className="col-lg-3 col-md-6" key={module.enclosureNodeID}>
                    <div className="panel panel-default text-center">
                        <div className="panel-heading">
                            <div className="row">
                                <div className="col-xs-9 text-left col-md-12">
                                    <span className="pull-right">
                                        <button type="button" className="btn btn-info btn-circle" onClick={this.openEditModal}>
                                            <i className="fa fa-edit"></i>
                                        </button>
                                        <button type="button" className="btn btn-danger btn-circle" onClick={this.openDeleteModal}>
                                            <i className="fa fa-times"></i>
                                        </button>
                                    </span>
                                    <div className="huge">{module.name}</div>
                                    <div>Pet Profile: {module.petProfileID}</div>
                                    <div> Optional Load: {opt_load}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            );
        }
        this.forceUpdate();
    },

    /* Edit Modal Management */

    isEmpty: function (value) {
        return !_.isEmpty(value);
    },

    openEditModal: function (){
        //console.log(value);
        this.setState({
            isEditModalOpen: true
        });
        //this.handleEditModule(value);
    },

    closeEditModal: function(){
        this.setState({
            isEditModalOpen: false
        });
    },

    handleEditModule: function(value) {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module/' + value,
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
            profile_selected: data
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
          profile_selected: this.state.petprofileID,
        });

        this.forceUpdate();
    },

     handleProfileChange: function(event) {
        for(var i = 0; i < this.state.profile_data.length; i++){
            var option = this.state.profile_data[i];
            if(event.target.value == option.petProfileID){
                this.setState({
                    profile_selected: option,
                    petprofileID: option.petProfileID,
                }, 
                    function(){
                        this.setState({
                            profile_selected: this.state.profile_selected,
                            petprofileID: this.state.petprofileID
                        });
                    }   
                );
            }
        }
    },

    handleModuleEditSubmit: function (event, value) {
       event.preventDefault();

        var canProceed = !_.isEmpty(this.state.modulename);

        if(canProceed) {

          var modData = {
            name: this.state.modulename,
            petProfileID: this.state.petprofileID,
            OPTIONAL_LOAD: parseInt(this.state.optionalLoad),
          }

          var url = 'http://dynocare.xyz/node_api/module/' + value;

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

    /* Delete Modal Management */
    openDeleteModal: function(value){
        console.log(value);
        this.setState({
            isDeleteModalOpen: true
        });
    },

    closeDeleteModal: function(){
        this.setState({
            isDeleteModalOpen: false
        });
    },

    handleDeleteModule: function (event, value) {
       e.preventDefault();

      var url = 'http://dynocare.xyz/node_api/module/' + value;

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

    
    render: function() {
        return (
            <div>
                <div {...this.props}>
                    {this.state.modules}
                </div>

                <div className="modal fade" role="dialog">
                    <Modal 
                        className="modal-dialog"
                        isOpen={this.state.isEditModalOpen} 
                        onCancel={this.closeEditModal}>

                        <div className="modal-dialog">

                            <div className="modal-content">

                                <div className="modal-header">
                                    <button type="button" className="close" aria-hidden="true" onClick={this.closeEditModal}>×</button>
                                    <h4 className="modal-title">Edit Module</h4>
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
                                    <button className="btn btn-default" onClick={this.closeEditModal}>Cancel</button>
                                    <button className="btn btn-primary" type="submit">Save Changes</button>
                                    <button className="btn btn-danger" data-toggle="modal" data-target="#removeModule">Remove Module</button>
                                </div>
                            </div>
                        </div>
                    </Modal>
                </div>

                 <div className="modal fade" role="dialog">
                    <Modal 
                        className="modal-dialog"
                        isOpen={this.state.isDeleteModalOpen} 
                        onCancel={this.closeDeleteModal}>

                        <div className="modal-dialog">

                            <div className="modal-content">

                                <div className="modal-header">
                                    <button type="button" className="close" aria-hidden="true" onClick={this.closeDeleteModal}>×</button>
                                    <h4 className="modal-title">Edit Module</h4>
                                </div>

                                <div className="modal-body">
                                    <form role="form" onSubmit={this.handleDeleteModule} method="DELETE">
                                        <p> Are you sure you want to delete this module?</p>      
                                  </form>
                                </div>

                                <div className="modal-footer">
                                  <button className="btn btn-default" onClick={this.closeDeleteModal}>Cancel</button>
                                  <button className="btn btn-primary" type="submit">Confirm</button>
                                </div>
                            </div>
                        </div>
                    </Modal>
                </div>
            </div>    
        );
    }
});

ReactDOM.render(<GetModulesData/>, document.getElementById('get-module-data'))