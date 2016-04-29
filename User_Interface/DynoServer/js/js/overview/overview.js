var React = require('react');
var ReactDOM = require('react-dom');

var Overview = React.createClass({

    /* verify session data */
    componentDidMount: function() {
        this.setupAjax();
        this.setState({
          idToken: this.getIdToken()
        });
    },

    setupAjax: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/overview',
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
            modules: [],
            data: [],
            selected: "",
            interval: ""
        }
    },

    /* Main Functions */

    loadModulesData: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/overview',
            dataType: 'json',
            beforeSend: function(xhr) {
                if (localStorage.getItem('token')) {
                  xhr.setRequestHeader('Authorization',
                        'Bearer ' + localStorage.getItem('token'));
                }
            },
            success: this.handleSuccess,
            complete: this.requestData,
        });
    },

    requestData: function(){
        this.setState({
            interval: setInterval(this.reloadModulesData, 3000)
        });
    },

    stopRequest: function(value){
        clearInterval(this.state.interval);

        this.setState({
            selected: value
        }, 
            function(){
                this.setState({
                    selected: this.state.selected
                });
            }
        );
    },

    reloadModulesData: function(){
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/overview',
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
            modules: []
        });
        this.getModules(data);
    },

    getModules: function(data){
        for (var i = 0; i < data.length; i++) {
            var module = data[i];
            var hm_status = "off";
            var ht_status = "off";
            var uv_status = "off";
            var op_status = "off";
            var status = "offline";

            if(module.HUM_STATUS == "1"){
                hm_status = "on";
            }
            if(module.HEAT_STATUS == "1"){
                ht_status = "on";
            }
            if(module.UV_STATUS == "1"){
                uv_status = "on";
            }
            if(module.OPTIONAL_STATUS == "1"){
                op_status = "on";
            }
            if(module.online){
                status = "online";
            }
            this.state.modules.push(
                <div key={module.enclosureNodeID}>
                    <div className="col-lg-3 col-md-6">
                        <div className="panel panel-primary text-center">
                            <div className="panel-heading">
                                <div className="row">
                                    <div className="col-xs-9 text-left col-md-12">
                                        <div className="huge">{module.enclosureName}</div>
                                        <div>Pet Profile: {module.petProfileID}</div>
                                        
                                        <div className="huge">{module.RH} % </div> R. Humidity
                                        <div className="huge">{module.TEMP} °F</div> Temperature
                                        
                                        <div>Humidifier: {hm_status}</div>
                                        <div>UV: {uv_status}</div>
                                        <div>Heating Lamp: {ht_status}</div>
                                        <div>Optional Load: {op_status}</div>
                                    </div>
                                </div>
                            </div>
                            <a href="#" data-toggle="modal" data-target="#viewDetails" onClick={this.stopRequest.bind(this,module)}>
                                <div className="panel-footer">
                                    <span className="pull-left">View Details</span>
                                    <span className="pull-right"><i className="fa fa-arrow-circle-right text-muted"></i></span>
                                    <div className="clearfix">
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            );
        }
        this.forceUpdate();
    },
    
    render: function() {
        return (
            <div>
                <div {...this.props}>
                    {this.state.modules}
                </div>

                <div className="modal fade" id="viewDetails" role="dialog">
                        <div className="modal-dialog">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <button type="button" className="close" data-dismiss="modal" aria-hidden="true" onClick={this.requestData}>×</button>
                                    <h4 className="modal-title">View Module Details</h4>
                                </div>
                                <div className="modal-body">
                                    <h2>{this.state.selected.name}</h2>
                                    
                                    <div className="row">
                                        <div className="col-lg-12">
                                            <div>Pet Profile: {this.state.selected.petProfileID}</div>
                                            <h2>Current Paramenters:</h2>
                                            <div>Humidity: {this.state.selected.RH} %</div>
                                            <div>Temperature: {this.state.selected.TEMP} °F</div>
                                            <div>Humidifier: {this.state.selected.HUM_STATUS}</div>
                                            <div>UV: {this.state.selected.UV_STATUS}</div>
                                            <div>Heating Lamp: {this.state.selected.HEAT_STATUS}</div>
                                            <div>Optional Load: {this.state.selected.OPTIONAL_STATUS}</div>
                                        </div>
                                        <div className="col-lg-12"> 
                                            <h2>Module Settings:</h2>
                                            <h3>Day:</h3>
                                            <div>Humidity: {this.state.selected.day_Humidity_SP} %</div>
                                            <div>Temperature: {this.state.selected.day_Temperature_SP} °F</div>
                                        
                                            <h3>Night:</h3>
                                            <div>Humidity: {this.state.selected.night_Humidity_SP} %</div>
                                            <div>Temperature: {this.state.selected.night_Temperature_SP} °F</div>
                                        </div>
                                        
                                        <div className="col-lg-12">
                                            <h2>Additional Settings:</h2>
                                            <div>Humidity Threshold: {this.state.selected.humidity_TH} %</div>
                                            <div>Temperature Threshold: {this.state.selected.temperature_TH} °F</div>
                                        </div>
                                    </div>
                                </div>
                                <div className="modal-footer">
                                    <a className="btn btn-default" data-dismiss="modal" onClick={this.requestData}>OK</a>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        );
    }
});

ReactDOM.render(<Overview/>, document.getElementById('overview'))