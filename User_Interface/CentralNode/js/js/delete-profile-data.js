var React = require('react');
var ReactDOM = require('react-dom');

var DeleteProfile = React.createClass({
  
	getInitialState: function(){
		return {
			profilename: "",
		};
	},

  	handleDeleteProfile: function (e) {
	   e.preventDefault();

      var url = 'http://localhost/node_api/profiles/' + this.state.profilename;

      jQuery.ajax({
        url: url,
        type: 'DELETE',

        success: function(response){
        	alert('Profile deleted.');
        },
        error: function(xhr, ajaxOptions, thrownError) {
	        if(xhr.status == 500){
	        	alert("Pet profile in use on a module. Please change module settings before trying to delete.");
	        }
	        else if(xhr.status == 404){
	        	alert("Profile does not exists.");
	        }
      	}
      }); 
	},

	componentDidMount: function() {
    jQuery.ajax({
        url: 'http://localhost/node_api/profiles',
        dataType: 'json',
        
        success: this.successHandler
    });
  },

  successHandler: function(data) {
      this.setState({
      	profilename: data[0].petProfileID,
      });
      this.forceUpdate();
  },

	render: function() {
	    return (
	      	<form role="form" onSubmit={this.handleDeleteProfile} method="DELETE">
		        <p> Are you sure you want to delete the pet profile {this.state.profilename}?</p>      

		        <div className="modal-footer">
		          <button className="btn btn-default" data-dismiss="modal">Cancel</button>
		          <button className="btn btn-primary" type="submit">Confirm</button>
		        </div>
		      </form>
	    );
	}
});

ReactDOM.render(<DeleteProfile/>,document.getElementById('delete-profile-data'))