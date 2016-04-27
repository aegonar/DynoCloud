var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('./components/text-input.js');
var RadioGroup = require('react-radio-group');
var _ = require('underscore');
var ModuleRender = require('./module-render.js');
var DeleteModal = require('./delete-module.js');
var EditModal = require('./edit-module.js');

var Modules = React.createClass({
    getInitialState: function() {
        return {
            data: [],
        }
    },

    loadModulesData: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module',
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
        this.loadModulesData();
    },
    
    render: function() {
        return ( 
            <div {...this.props}>
            {
                this.state.data.map(function(element){
                    return(
                        <div key={element.enclosureNodeID}>
                            <ModuleRender enclosureNodeID={element.enclosureNodeID}/> 
                            <DeleteModal enclosureNodeID={element.enclosureNodeID}/>
                            <EditModal enclosureNodeID={element.enclosureNodeID}/>
                        </div>
                    );
                })
            }
            </div>
        );
    }
});

ReactDOM.render(<Modules/>, document.getElementById('module'))