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

    isEmpty: function (value) {
        return !_.isEmpty(value);
    },

    handleModuleEditSubmit: function (event) {
       event.preventDefault();

        var canProceed = !_.isEmpty(this.state.modulename);

        if(canProceed) {

          var modData = {
            name: this.state.modulename,
            petProfileID: this.state.petprofileID,
            OPTIONAL_LOAD: parseInt(this.state.optionalLoad),
          }

          var url = 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID;

          jQuery.ajax({
            url: url,
            dataType: 'json',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify( modData ),
          });
        } 
    },

    handleModuleNameInput: function(event){
        this.setState({
            modulename: event.target.value
        });
    },

    handleOptLoadChange: function(value){
        this.setState({
            optionalLoad: value
        });
    },

    handleEditModule: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module/' + this.state.enclosureNodeID,
            dataType: 'json',

            success: this.successEditHandler,
            complete: this.getProfileData
        })
    },

    successEditHandler: function(data) {
        this.setState({
            enclosureNodeID: data.enclosureNodeID,
            petprofileID: data.petProfileID,
            modulename: data.name,
            optionalLoad: parseInt(data.OPTIONAL_LOAD),
        });
        console.log(data);
        console.log(this.state.petprofileID);
        this.forceUpdate();
    },

    getProfileData: function(){
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/profiles',
            dataType: 'json',
            
            success: this.successHandleDataProfiles
        })
    },

    successHandleDataProfiles: function(data) {

        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            this.state.options.push(
                <option key={option.petProfileID} value={option.value}>{option.petProfileID}</option>
            );
            this.state.profile_data.push(option);
        }

        this.setState({
          selected: this.state.petprofileID,
        });
        this.forceUpdate();
    },

    handleProfileChange: function(event) {
        for(var i = 0; i < this.state.profile_data.length; i++){
            var option = this.state.profile_data[i];
            if(event.target.value == option.petProfileID){
                this.setState({
                    selected: option,
                    petprofileID: option.petProfileID,
                }, 
                    function(){
                        this.setState({
                            selected: this.state.selected,
                            petprofileID: this.state.petprofileID
                        });
                    }   
                );
            }
        }
    },

    clearProfilesOptions: function(){
        this.setState({
            options: []
        });
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
        this.getModules(data);
    },

    componentDidMount: function() {
        this.loadModulesData();
    },

    getModules: function(data){
        this.setState({
            modules: data
        });
        this.forceUpdate();
    },
    
    render: function() {
        return (
            
            <div {...this.props}>
            {
                

                this.state.modules.map(function(element){
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