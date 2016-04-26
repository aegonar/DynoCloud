var React = require('react');
var ReactDOM = require('react-dom');
var EditModule = require('./edit-module-data.js');


var GetModulesData = React.createClass({
    getInitialState: function() {
        return {
            modules: [],
        }
    },
    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/module',
            dataType: 'json',
            
            success: this.successHandler
        })
    },

    getProfileData: function(data) {
        return null;
    },

    getOptionalLoadData: function(data){
        return null;
    },

    successHandler: function(data) {
        var opt_load = "None";
        for (var i = 0; i < data.length; i++) {
            var module = data[i];
            if(module.OPTIONAL_LOAD == 1){
                opt_load = "Infrared";
            }
            else if(module.OPTIONAL_LOAD == 2){
                opt_load = "Heating Lamp";
            }
            this.state.modules.push(
                <div className="col-lg-3 col-md-6" key={module.enclosureNodeID}>
                    <div className="panel panel-default text-center">
                        <div className="panel-heading">
                            <div className="row">
                                <div className="col-xs-9 text-left col-md-12">
                                    <div className="huge">{module.name}</div>
                                    <div>Pet Profile: {module.petProfileID}</div>
                                    <div> Optional Load: {opt_load}</div>
                                </div>
                            </div>
                        </div>
                        <a href="#" data-toggle="modal" data-target="#editModule">
                            <div className="panel-footer">
                                <span className="pull-left">Edit Module</span>
                                <span className="pull-right"><i class="fa fa-arrow-circle-right text-muted"></i></span>
                            <div className="clearfix">
                            </div>
                            </div>
                        </a>
                    </div>
                </div>
            );
        }
        this.forceUpdate();
    },
    
    render: function() {
        return (
          <div {...this.props}>
            {this.state.modules}
          </div>
        );
    }
});

ReactDOM.render(<GetModulesData/>, document.getElementById('get-module-data'))