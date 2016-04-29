var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('../components/text-input.js');
var RadioGroup = require('react-radio-group');
var _ = require('underscore');
var ModuleRender = require('./module-render.js');
var EditModal = require('./edit-module.js');

var Modules = React.createClass({

    componentDidMount: function() {
        this.setupAjax();
        this.setState({
          idToken: this.getIdToken()
        });
    },

    setupAjax: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/module',
            dataType: 'json',
          beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
          success: this.loadModulesData()
        });
    },

    getIdToken: function() {
        return localStorage.getItem('token');;
    },

    getInitialState: function() {
        return {
            data: [],
        }
    },

    loadModulesData: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/module',
            dataType: 'json',
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
            success: this.handleSuccess,
        });
    },

    handleSuccess: function(data){
        this.setState({
            data: data,
        });
    },
    
    render: function() {
        return ( 
            <div {...this.props}>
            {
                this.state.data.map(function(element){
                    return(
                        <div key={element.enclosureNodeID}>
                            <ModuleRender centralNodeID={element.centralNodeID} enclosureNodeID={element.enclosureNodeID}/>
                            <EditModal centralNodeID={element.centralNodeID} enclosureNodeID={element.enclosureNodeID}/>
                        </div>
                    );
                })
            }
            </div>
        );
    }
});

ReactDOM.render(<Modules/>, document.getElementById('module'))