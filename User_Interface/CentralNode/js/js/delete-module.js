var React = require('react');
var ReactDOM = require('react-dom');

var DeleteModule = React.createClass({
    getInitialState: function() {
        return {
            enclosureNodeID: this.props.enclosureNodeID,
            modulename: ""
        }
    },

    loadModuleData: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID,
            dataType: 'json',

            success: this.handleSuccess
        });
    },

    handleSuccess: function(data){
        this.setState({
            modulename: data.name
        });
    },

    componentDidMount: function() {
        this.loadModuleData();
    },

    componentWillReceiveProps: function (newProps) {    
        if(newProps.enclosureNodeID) {
            this.setState({
              enclosureNodeID: newProps.enclosureNodeID
            });
        }   
    },

    handleDeleteModule: function (event) {
       event.preventDefault();

      var url = 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID;

      jQuery.ajax({
        url: url,
        type: 'DELETE',

        success: function(response){
            this.loadModulesData;
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
                                    <p> Are you sure you want to delete this module {this.state.modulename}?</p>      
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
        );
    }
});

module.exports = DeleteModule;