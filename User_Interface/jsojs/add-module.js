var React = require('react');
var ReactDOM = require('react-dom');
var Select = require('./components/profiles.js');

var OPTIONS = require('./data/default-profiles.js');

var AddModule= React.createClass({
  
	getInitialState: function(){
		return {
			id: 0,
			modulename: '',
			petprofile: '',
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

  	handleModuleCreateSubmit: function (e) {
	    e.preventDefault();
	    var canProceed = !_.isEmpty(this.state.modulename); 

	    if(canProceed) {
	      var modData = {        
	        id: this.state.id + 1,
	        modulename: this.state.modulename,
	        petprofile: this.state.petprofile
	      };

	      this.handleModuleCreatedSuccess(modData);

	      /* Open MCU config and peripherals modal*/
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
	          <input 
	            className="form-control" 
	            type="text" 
	            ref="moduleName"
	            validate={this.isEmpty}
	            value={this.state.modulename}
	            onChange={this.handleModuleNameInput}/>
	        </div>

	        <div className="form-group">
	          <label>Select Pet Profile</label>
	          <Select
	          	className="form-control"
				value={this.state.petprofile}
				onChange={this.updateValue}/>
	        </div>

	        <div className="modal-footer">
	            <a className="btn btn-default" data-dismiss="modal">Cancel</a>
	            <a className="btn btn-primary" data-toggle="modal" data-target="#connecting">Save changes</a>
	          </div>
	        </form>
	    );
	}
});

ReactDOM.render(<AddModule/>,document.getElementById('create-module-form'))