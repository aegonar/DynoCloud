var React = require('react');
var ReactDOM = require('react-dom');
var RadioGroup = require('react-radio-group');
var Override = require('./override.js');


var Overview = React.createClass({
    getInitialState: function() {
        return {
            modules: [],
            data: [],
        }
    },

    handleOverride: function(){
        document.getElementById('changes').style.visibility="visible";
    },

    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/node_api/overview',
            dataType: 'json',
            success: this.successHandler
        });
       //setInterval(function() {
       //    jQuery.ajax({
       //         url: 'http://dynocare.xyz/node_api/overview',
       //         dataType: 'json',
       //         success: this.successHandler
       //    })
       //}, 1000 * 3);
    },

    successHandler: function(data) {

        for (var i = 0; i < data.length; i++) {
            var module = data[i];

            this.state.data.push(module);

            this.state.modules.push(
                <div className="col-lg-3 col-md-6" key={module.enclosureNodeID}>
                    <div className="panel panel-primary text-center">
                        <div className="panel-heading">
                            <div className="row">
                                <div className="col-xs-9 text-left col-md-12">
                                    <div className="huge">{module.enclosureName}</div>
                                    <div>Pet Profile: {module.petProfileID}</div>
                                    
                                    <div className="huge">{module.RH} %/{module.TEMP} F</div>
                                    <div>Humidifier: {module.HUM_STATUS}</div>
                                    <div>UV: {module.UV_STATUS}</div>
                                    <div>Heating Lamp: {module.HEAT_STATUS}</div>
                                    <div>Optional Load: {module.OPTIONAL_STATUS}</div>

                                    <Override 
                                        enclosureNodeID={module.enclosureNodeID}/>
                                </div>
                            </div>
                        </div>
                        <a href="#" data-toggle="modal" data-target="#viewDetails">
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
        console.log(this.state.data);
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

ReactDOM.render(<Overview/>, document.getElementById('overview'))