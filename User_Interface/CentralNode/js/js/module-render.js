var React = require('react');
var ReactDOM = require('react-dom');

var GetModuleData = React.createClass({
    getInitialState: function() {
        return {
            data: [],
            enclosureNodeID: this.props.enclosureNodeID
        }
    },

    loadModuleData: function() {
        jQuery.ajax({
            url: 'http://localhost/node_api/module/' + this.state.enclosureNodeID,
            dataType: 'json',
            success: this.handleSuccess,
        });
    },

    handleSuccess: function(data){
        this.setState({
            data: data,
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
            </div>
        );
    }
});

module.exports = GetModuleData;