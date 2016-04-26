var React = require('react');
var ReactDOM = require('react-dom');

var DeleteModule = React.createClass({
  
	getInitialState: function(){
		return {
			enclosureNodeID: 2,
		};
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

	componentDidMount: function() {
      jQuery.ajax({
          url: 'http://dynocare.xyz/node_api/module',
          dataType: 'json',
          
          success: this.successHandler
      });
    },

    successHandler: function(data) {
      this.setState({
      	enclosureNodeID: data.enclosureNodeID,
      });
      this.forceUpdate();
    },

	render: function() {
	    return (
	      	<form role="form" onSubmit={this.handleDeleteModule} method="DELETE">
		        <p> Are you sure you want to delete this module?</p>      

		        <div className="modal-footer">
		          <button className="btn btn-default" data-dismiss="modal">Cancel</button>
		          <button className="btn btn-primary" type="submit">Confirm</button>
		        </div>
		      </form>
	    );
	}
});

ReactDOM.render(<DeleteModule/>,document.getElementById('delete-module'))