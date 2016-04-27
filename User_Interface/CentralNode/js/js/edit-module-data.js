var React = require('react');
var ReactDOM = require('react-dom');
var Modal = require('react-modal');
var RadioGroup = require('react-radio-group');
var _ = require('underscore');
var TextInput = require('./components/text-input.js');
var Override = require('./override.js');

var EditModule= React.createClass({
  
	getInitialState: function(){
		return {
			enclosureNodeID: "",
			modulename: null,
			petprofileID: "",
			optionalLoad: "0",
			options: [],
            data:[],
            selected: {},
            modalIsOpen: false
		};
	},

	isEmpty: function (value) {
	    return !_.isEmpty(value);
	},

	openModal: function(){
		this.setState({
			modalIsOpen: true;
		});	
	},	

	closeModal: function(){
		this.setState({
			modalIsOpen: false;
		});
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

            success: this.successHandler,
            complete: this.getProfileData
        })
    },

    successHandler: function(data) {
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
    	console.log(data);

        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            this.state.options.push(
                <option key={option.petProfileID} value={option.value}>{option.petProfileID}</option>
            );
            this.state.data.push(option);
        }

        this.setState({
          selected: this.state.petprofileID,
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
	    	<Modal
	    		ref="EditModule"
	    		isOpen={this.state.modalIsOpen}
	    		onAfterOpen={this.handleEditModule}
	    		onRequestClose={this.closeModal}>
				<ModalHeader text="Edit Module Data" />
				<ModalBody>
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

			        	<div className="form-group">
			        		<Override enclosureNodeID={this.state.enclosureNodeID}/>
			        	</div>
				        
			        	<div className="modal-footer">
			            	<button className="btn btn-default" data-dismiss="modal">Cancel</button>
			            	<button className="btn btn-primary" type="submit">Save Changes</button>
			            	<button className="btn btn-danger" data-toggle="modal" data-target="#removeModule">Remove Module</button>
			          	</div>
			        </form>
				</ModalBody>
				<ModalFooter>
					<Button type="primary">Modal Footer</Button>
					<Button type="link-cancel">Button</Button>
				</ModalFooter>
			</Modal>
	    );
	}
});

module.exports = EditModule;