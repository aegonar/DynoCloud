var React = require('react');
var ReactDOM = require('react-dom');
var Testing = require('./testing.js');

var GetModulesData = React.createClass({
	getInitialState: function() {
        return {
        	modules: []
        }
    },

    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module',
            dataType: 'json',
            
            success: this.successHandler
        })
    },

    successHandler: function(data){
    	this.setState({
    		modules: data
    	});
    },

    render: function() {
        return (
            <div {...this.props}>
                {
                	this.state.modules.map(function(element){
                		return (<Testing key={element.enclosureNodeID} enclosureNodeID={element.enclosureNodeID}/>);
                	},this)
                }
            </div>
        );
    }

});

ReactDOM.render(<GetModulesData/>, document.getElementById('get-module-data'))