var React = require('react');
var ReactDOM = require('react-dom');
var TextInput = require('./components/text-input.js');
var RadioGroup = require('react-radio-group');
var _ = require('underscore');
var ModuleRender = require('./module-render.js');

var Modules = React.createClass({
    getInitialState: function() {
        return {
            data: [],
            modules: [],

            modules_data:{},

            modulename: null,
            enclosureNodeID: "",
            petprofileID: "",
            optionalLoad: "0",
            options: [],
            profile_data:[],
            selected: {},
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
                    console.log(element);
                    return(
                        <div key={element.enclosureNodeID}>
                            <ModuleRender enclosureNodeID={element.enclosureNodeID}/>
                        </div>   
                    );
                })
            }
            </div>


        );
    }
});

ReactDOM.render(<Modules/>, document.getElementById('module'))