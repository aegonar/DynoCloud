var React = require('react');
var ReactDOM = require('react-dom');

var GetModuleData = React.createClass({
    getInitialState: function() {
        return {
            data: [],
            modules: [],
            enclosureNodeID: ""
        }
    },

    handleDeleteModule: function (event) {
       event.preventDefault();

      var url = 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID;

      jQuery.ajax({
        url: url,
        type: 'DELETE',

        success: function(response){
            alert('deleted');
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

    loadModuleData: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID,
            dataType: 'json',
            success: this.handleSuccess,
        });
    },

    handleSuccess: function(data){
        console.log(data);
        this.setState({
            data: data,
        });
    },

    componentDidMount: function() {
        this.loadModuleData();
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
                                        <p> Are you sure you want to delete this module {this.state.data.name}?</p>      
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

module.exports = GetModuleData;