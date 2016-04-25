var React = require('react');
var ReactDOM = require('react-dom');
//var Header = require('./data/login-data');


var GetProfilesData = React.createClass({
    getInitialState: function() {
        return {
            options: [],
            data:[],
            selected: {}
        }
    },
    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/profiles',
            dataType: 'json',
            
            success: this.successHandler
        })
    },
    
    successHandler: function(data) {
        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            this.state.options.push(
                <option key={option.petProfileID} value={option.value}>{option.petProfileID}</option>
            );
            this.state.data.push(option);
        }

        this.setState({
          selected: this.state.data[0],
        });

        this.forceUpdate();
    },

    handleChange: function(event) {
        for(var i = 0; i < this.state.data.length; i++){
          var option = this.state.data[i];
          if(event.target.value == option.petProfileID){
            this.setState({
              selected: option,
            });
          }
        }
    },

    render: function() {
        return (
          <div>
            <div {...this.props}>
                <select onChange={this.handleChange}>
                    {this.state.options}
                </select>
            </div>
            <div>
              <ul>
                <li>Temperature Threshold: {this.state.selected.temperature_TH}</li>
                <li>Humidity Threshold: {this.state.selected.humidity_TH}</li>
                <h4>Day:</h4>
                <li>Time: </li>
                <li>Temperature Set Point: {this.state.selected.day_Temperature_SP}</li>
                <li>Humidity Set Point: {this.state.selected.day_Humidity_SP}</li>
                <h4>Night:</h4>
                <li>Time: </li>
                <li>Temperature Set Point: {this.state.selected.night_Temperature_SP}</li>
                <li>Humidity Set Point: {this.state.selected.night_Humidity_SP}</li>
              </ul>
            </div>
          </div>
        );
    }
});

ReactDOM.render(<GetProfilesData/>, document.getElementById('get-profiles-data'))