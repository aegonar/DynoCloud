var React = require('react');
var ReactDOM = require('react-dom');


var Overview = React.createClass({
    getInitialState: function() {
        return {
            modules: [],
            data: [],
            interval: ""
        }
    },

    loadModulesData: function() {
        jQuery.ajax({
            url: 'http://localhost/node_api/overview',
            dataType: 'json',
            success: this.handleSuccess,
            complete: this.requestData,
        });
    },

    requestData: function(){
        this.setState({
            interval: setInterval(this.reloadModulesData, 3000)
        });
    },

    stopRequest: function(){
        clearInterval(this.state.interval);
    },

    reloadModulesData: function(){
        jQuery.ajax({
            url: 'http://localhost/node_api/overview',
            dataType: 'json',
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

    componentDidMount: function() {
        this.loadModulesData();
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
                            <a href="#" data-toggle="modal" data-target="#viewDetails" onClick={this.stopRequest}>
                                <div className="panel-footer">
                                    <span className="pull-left">View Details</span>
                                    <span className="pull-right"><i className="fa fa-arrow-circle-right text-muted"></i></span>
                                <div className="clearfix">
                                </div>
                                </div>
                            </a>
                        </div>
                    </div>

                    <div className="modal fade" id="viewDetails" role="dialog">
                        <div className="modal-dialog">
                            <div className="modal-content">
                                <div className="modal-header">
                                    <button type="button" className="close" data-dismiss="modal" aria-hidden="true" onClick={this.requestData}>×</button>
                                    <h4 className="modal-title">View Module Details</h4>
                                </div>
                                <div className="modal-body">
                                    <h2>{module.name}</h2>
                                    
                                    <div className="row">
                                        <div className="col-lg-12">
                                            <div>Pet Profile: {module.petProfileID}</div>
                                            <h2>Current Paramenters:</h2>
                                            <div>Humidity: {module.RH} %</div>
                                            <div>Temperature: {module.TEMP} °F</div>
                                            <div>Humidifier: {hm_status}</div>
                                            <div>UV: {uv_status}</div>
                                            <div>Heating Lamp: {ht_status}</div>
                                            <div>Optional Load: {op_status}</div>
                                        </div>
                                        <div className="col-lg-12"> 
                                            <h2>Module Settings:</h2>
                                            <h3>Day:</h3>
                                            <div>Humidity: {module.day_Humidity_SP} %</div>
                                            <div>Temperature: {module.day_Temperature_SP} °F</div>
                                        
                                            <h3>Night:</h3>
                                            <div>Humidity: {module.night_Humidity_SP} %</div>
                                            <div>Temperature: {module.night_Temperature_SP} °F</div>
                                        </div>
                                        
                                        <div className="col-lg-12">
                                            <h2>Additional Settings:</h2>
                                            <div>Humidity Threshold: {module.humidity_TH} %</div>
                                            <div>Temperature Threshold: {module.temperature_TH} °F</div>
                                            <div>ONLINE STATUS: {status}</div>
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