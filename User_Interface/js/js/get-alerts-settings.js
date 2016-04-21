var React = require('react');
var ReactDOM = require('react-dom');
//var Header = require('./data/login-data');


var GetAlertsData = React.createClass({
    getInitialState: function() {
        return {
            alerts: [],
        }
    },

    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/alerts',
            dataType: 'json',
            beforeSend: function (xhr) {
              xhr.setRequestHeader ("Authorization", "Bearer 56me538k6mevqf41tvjqe10nqj");
            },
            success: this.successHandler
        })
    },
    
    successHandler: function(data) {
        for (var i = 0; i < data.length; i++) {
            var alert = data[i];
            this.state.alerts.push(
                <div key={alert.enclosureNodeID} className="alert alert-danger">
                    {alert.message} 
                    <a href="#" className="alert-link">   Module: {alert.enclosureNodeID}</a>
                </div>
            );
        }

        this.forceUpdate();
    },

    render: function() {
        return (
          <div>
            <div {...this.props}>
                {this.state.alerts}
            </div>
          </div>
        );
    }
});

ReactDOM.render(<GetAlertsData/>, document.getElementById('get-alerts-data'))