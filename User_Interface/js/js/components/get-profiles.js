var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var Select = require('./profiles.js');

var OPTIONS = require('../data/default-profiles.js');

var GetProfilesData= React.createClass({
  
	getInitialState: function(){
		return {
			id: 0,
			petprofile: null,
			options: {OPTIONS}
		};
	},

  	updateValue: function(newValue) {
		this.setState({
			petprofile: newValue
		});
	},

	render: function() {
	    return (

		        <div className="form-group">
		          <label>Select Pet Profile</label>
		          <Select
		          	className="form-control"
					value={this.state.petprofile}
					onChange={this.updateValue}/>
		        </div>
	    );
	}
});

ReactDOM.render(<GetProfilesData/>,document.getElementById('get-profiles-data'))