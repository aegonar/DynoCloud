var React = require('react');
var ReactDOM = require('react-dom');
var RadioGroup = require('react-radio-group');


var Overview = React.createClass({
    getInitialState: function() {
        return {
            modules: [],
            data: [],
        }
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
       //}, 1000 * 3 *60);
    },

    successHandler: function(data) {

        for (var i = 0; i < data.length; i++) {
            var module = data[i];
            var hm_status = "off";
            var ht_status = "off";
            var uv_status = "off";
            var op_status = "off";

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

            this.state.data.push(module);

            this.state.modules.push(
                <div className="col-lg-3 col-md-6" key={module.enclosureNodeID}>
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
                        <a href="#" data-toggle="modal" data-target="#viewDetails">
                            <div className="panel-footer">
                                <span className="pull-left">View Details</span>
                                <span className="pull-right"><i className="fa fa-arrow-circle-right text-muted"></i></span>
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

ReactDOM.render(<Overview/>, document.getElementById('overview'))