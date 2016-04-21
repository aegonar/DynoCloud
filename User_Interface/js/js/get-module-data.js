var React = require('react');
var ReactDOM = require('react-dom');
//var Header = require('./data/login-data');


var GetModulesData = React.createClass({
    getInitialState: function() {
        return {
            modules: [],
        }
    },
    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/module',
            dataType: 'json',
            beforeSend: function (xhr) {
              xhr.setRequestHeader ("Authorization", "Bearer 56me538k6mevqf41tvjqe10nqj");
            },
            success: this.successHandler
        })
    },
    
    successHandler: function(data) {
        console.log(data);
    },

    render: function() {
        return (
          <div>
            <div class="huge">{this.modules.name}</div>
                <div>Chamaleon</div>
                <img src="../../images/pet_profiles/chamaleon.jpg" class="center-block img-circle img-responsive">
                <div>
                  <h3>Day: --:--</h3>
                </div>
                <div>Humidity:</div>
                <div>Temperature:</div>
                <div>Humidifier (%):</div>
                <div>UV (%):</div>
                <div>IR (%):</div>
                <div>IC (%):</div>
                <div>
                  <h3>Night: --:--</h3>
                </div>
                <div>Humidity:</div>
                <div>Temperature:</div>
                <div>Humidifier (%):</div>
                <div>UV (%):</div>
                <div>IR (%):</div>
                <div>IC (%):</div>
          </div>
        );
    }
});

ReactDOM.render(<GetModulesData/>, document.getElementById('get-module-data'))