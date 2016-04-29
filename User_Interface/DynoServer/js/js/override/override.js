var React = require('react');
var ReactDOM = require('react-dom');
var RadioGroup = require('react-radio-group');

var Override = React.createClass({
  
	getInitialState: function(){
		return {
			enclosureNodeID: this.props.enclosureNodeID,
		    HUM_OR: "0",
		    HEAT_OR: "0",
		    UV_OR: "0",
		    OPTIONAL_OR: "0",
		    HUM_STATUS: "0",
		    HEAT_STATUS: "0",
		    UV_STATUS: "0",
		    OPTIONAL_STATUS: "0"
		};
	},

  	handleOverrideSubmit: function (e) {
	   e.preventDefault();

		var overrideData = {
		    HUM_OR: this.state.HUM_OR,
		    HEAT_OR: this.state.HEAT_OR,
		    UV_OR: this.state.UV_OR,
		    OPTIONAL_OR: this.state.OPTIONAL_OR,

		    HUM_STATUS: this.state.HUM_STATUS,
		    HEAT_STATUS: this.state.HEAT_STATUS,
		    UV_STATUS: this.state.UV_STATUS,
		    OPTIONAL_STATUS: this.state.OPTIONAL_STATUS
		}

		//var url = 'http://dynocare.xyz/api/override/' + this.state.centralNodeID + '/' + this.state.enclosureNodeID,

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
		});
	},

	componentWillMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/override/' + this.state.centralNodeID + '/' + this.state.enclosureNodeID,
            dataType: 'json',
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
            success: this.successOverrideHandler
        })
    },

    successOverrideHandler: function(data) {
        this.setState({
    		enclosureNodeID: data.enclosureNodeID,
		    HUM_OR: data.HUM_OR,
		    HEAT_OR: data.HEAT_OR,
		    UV_OR: data.UV_OR,
		    OPTIONAL_OR: data.OPTIONAL_OR,
		    
		    HUM_STATUS: data.HUM_STATUS,
		    HEAT_STATUS: data.HEAT_STATUS,
		    UV_STATUS: data.UV_STATUS,
		    OPTIONAL_STATUS: data.OPTIONAL_STATUS
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
		});
    },

    handleHeatLampStatus: function(value){
    	this.setState({
			HEAT_STATUS: value
		});
    },

    handleUltravioletStatus: function(value){
    	this.setState({
			UV_STATUS: value
		});
    },

    handleOptionalStatus: function(value){
    	this.setState({
			OPTIONAL_STATUS: value
		});
    },

    componentWillReceiveProps: function (newProps) {     
        if(newProps.enclosureNodeID) {
            this.setState({
              enclosureNodeID: newProps.enclosureNodeID
            });  
        }
    },

	render: function() {
	    return (
	      	<div role="form">
		        <div className="form-group">
		        	<div className="checkbox">
	              		<label>
	                		<input type="checkbox" checked={this.state.HUM_OR} onChange={this.handleHumidifierEnable}/>Humidifier
	            		</label>
            		</div>
		        	<RadioGroup id="humidifier" name="humidifier" selectedValue={this.state.HUM_STATUS} onChange={this.handleHumidifierStatus}>
  						{Radio => (
						    <div>
						      <Radio value="1" /> on <br/>
						      <Radio value="0" /> off <br/> 
						    </div>
					  	)}
					</RadioGroup>
            	</div>

            	<div className="form-group">
		        	<div className="checkbox">
	              		<label>
	                		<input type="checkbox" checked={this.state.HEAT_OR} onChange={this.handleHeatLampEnable}/>Heat Lamp
	            		</label>
            		</div>
		        	<RadioGroup id="heat_lamp" name="heat_lamp" selectedValue={this.state.HEAT_STATUS} onChange={this.handleHeatLampStatus}>
  						{Radio => (
						    <div>
						      <Radio value="1" /> on <br/>
						      <Radio value="0" /> off <br/> 
						    </div>
					  	)}
					</RadioGroup>
            	</div>

            	<div className="form-group">
		        	<div className="checkbox">
	              		<label>
	                		<input type="checkbox" checked={this.state.UV_OR} onChange={this.handleUltravioletEnable}/>Ultraviolet (UV)
	            		</label>
            		</div>
		        	<RadioGroup id="ultraviolet" name="ultraviolet" selectedValue={this.state.UV_STATUS} onChange={this.handleUltravioletStatus}>
  						{Radio => (
						    <div>
						      <Radio value="1" /> on <br/>
						      <Radio value="0" /> off <br/> 
						    </div>
					  	)}
					</RadioGroup>
            	</div>

            	<div className="form-group">
		        	<div className="checkbox">
	              		<label>
	                		<input type="checkbox" checked={this.state.OPTIONAL_OR} onChange={this.handleOptionalEnable}/>OPTIONAL
	            		</label>
            		</div>
		        	<RadioGroup id="optional" name="optional" selectedValue={this.state.OPTIONAL_STATUS} onChange={this.handleOptionalStatus}>
  						{Radio => (
						    <div>
						      <Radio value="1" /> on <br/>
						      <Radio value="0" /> off <br/> 
						    </div>
					  	)}
					</RadioGroup>
				</div>

            	<div>
                    <button className="btn btn-xs btn-default" id="changes" type="submit" onSubmit={this.handleOverrideSubmit} method="POST">Add Override</button>
                </div> 
	        </div>
	    );
	}
});

module.exports = Override;