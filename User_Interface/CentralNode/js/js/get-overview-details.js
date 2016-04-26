var React = require('react');
var ReactDOM = require('react-dom');


var ModuleDetails = React.createClass({
    getInitialState: function() {
        return {
            data: [],
            enclosureNodeID: "0"
        }
    },

    loadModulesData: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/overview/' + this.state.enclosureNodeID,
            dataType: 'json',
            success: this.handleSuccess
        });
    },

    handleSuccess: function(data){
        this.setState({
            data: data,
            modules: []
        });
        this.getModules(data);
    },

    componentDidMount: function() {
        this.loadModulesData();
    },

module.exports = ModuleDetails;