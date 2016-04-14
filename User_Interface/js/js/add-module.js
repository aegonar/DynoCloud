var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('./components/text-input.js');
var _ = require('underscore');
var Select = require('./components/profiles.js');

var OPTIONS = require('./data/default-profiles.js');

var AddModule= React.createClass({
  
	getInitialState: function(){
		return {
			id: 0,
			modulename: null,
			petprofile: null,
			options: {OPTIONS}
		};
	},

	isEmpty: function (value) {
	    return !_.isEmpty(value);
	},

	handleModuleNameInput: function(event){
    	this.setState({
      		modulename: event.target.value
    	});
  	},

  	handleModuleCreatedSuccess: function(data){
  		console.log("Success. Module name is: " + data.name);
  	},

  	handleModuleCreateSubmit: function (e) {
	    e.preventDefault();
	    var canProceed = true; 

	    console.log(canProceed);

	    if(canProceed) {
	      var modData = {        
	        id: this.state.id + 1,
	        name: this.state.modulename,
	        value: this.state.petprofile
	      };

	      this.handleModuleCreatedSuccess(modData);

	      /* Open MCU config and peripherals modal*/
	      console.log("Your new module name is: " + modData.name);
	      alert('New module created.');
	    } 
	    else {
	      !_.isEmpty(this.state.modulename);
	    }
	  },

  	updateValue: function(newValue) {
		this.setState({
			petprofile: newValue
		});
	},

	render: function() {
	    return (
	      	<form role="form" onSubmit={this.handleModuleCreateSubmit} method="POST">

		        <div className="form-group">
		          <label>Module Name</label>
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
		          <label>Select Pet Profile</label>
		          <Select
		          	className="form-control"
					value={this.state.petprofile}
					onChange={this.updateValue}/>
		        </div>

		        	<div className="modal-footer">
		            <button className="btn btn-default" data-dismiss="modal">Cancel</button>
		            <button className="btn btn-primary" type="submit">Create Module</button>
		          </div>
	        </form>
	    );
	}
});

ReactDOM.render(<AddModule/>,document.getElementById('create-module-form'))