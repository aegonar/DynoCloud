var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('./components/text-input.js');
var _ = require('underscore');
var RadioGroup = require('react-radio-group');

var AddModule= React.createClass({
  
	getInitialState: function(){
		return {
			modulename: null,
			petprofileID: "",
			optionalLoad: "0",
			options: [],
            data:[],
            selected: {}
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
	        name: this.state.modulename,
	        petProfileID: this.state.petprofileID,
	        OPTIONAL_LOAD: parseInt(this.state.optionalLoad),
	      }

	      var url = 'http://dynocare.xyz/node_api/module';

	      jQuery.ajax({
	        url: url,
	        dataType: 'json',
	        type: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify( modData ),
	      });
	    } 
	    else {
	      this.refs.moduleName.isValid();
	    }
	},

	handleOptLoadChange: function(value){
		this.setState({
			optionalLoad: value
		});
	},

	componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/profiles',
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
          petprofileID: this.state.data[0].petProfileID
        });
        this.forceUpdate();
    },

    handleProfileChange: function(event) {
        for(var i = 0; i < this.state.data.length; i++){
          	var option = this.state.data[i];
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
	      	<form role="form" onSubmit={this.handleModuleCreateSubmit} method="POST">

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
		                <select onChange={this.handleProfileChange}>
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
		        
	        	<div className="modal-footer">
	            	<button className="btn btn-default" data-dismiss="modal">Cancel</button>
	            	<button className="btn btn-primary" type="submit">Create Module</button>
	          	</div>
	        </form>
	    );
	}
});

ReactDOM.render(<AddModule/>,document.getElementById('add-module'))