var React = require('react');
var ReactDOM = require('react-dom');

var GetAlertsData = React.createClass({
    getInitialState: function() {
        return {
            alerts: [],
        }
    },

    handleAlertDismiss: function(){

    },

    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/alerts',
            dataType: 'json',
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
            success: this.successHandler
        })
    },
    
    successHandler: function(data) {
        for (var i = 0; i < data.length; i++) {
            var alert = data[i];
            this.state.alerts.push(
                <div key={alert.alertID} className="alert alert-danger alert-dismissable">
                    <button type="button" className="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    {alert.message}  
                    <a href="#" className="alert-link">   Module: {alert.enclosureNodeID}</a>
                    <a href="#" className="alert-link">   Central Node: {alert.centralNodeID}</a>
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