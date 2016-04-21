var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');


var EditAlerts = React.createClass({
  
  getInitialState: function(){
    return {
      userID: null,
      retries: null,
      email: null,
      phone: null,
      onScreen: null,
      threshold: null
    };
  },

  handleEditAlerts: function (e) {
    e.preventDefault();

    var alertsSettData = {
      email: this.state.email,
      phone: this.state.phone,
      onScreen: this.state.onScreen,
      retries: this.state.retries,
      threshold: this.state.threshold,
    };

    var url = 'http://dynocare.xyz/api/alerts/settings';

    jQuery.ajax({
      url: url,
      dataType: 'json',
      type: 'PUT',
      contentType: 'application/json',
      data: JSON.stringify( alertsSettData ),

      beforeSend: function (xhr) {
        xhr.setRequestHeader ("Authorization", "Bearer 56me538k6mevqf41tvjqe10nqj");
      },
    });
  },

  componentDidMount: function() {
    jQuery.ajax({
        url: 'http://dynocare.xyz/api/alerts/settings',
        dataType: 'json',
        beforeSend: function (xhr) {
          xhr.setRequestHeader ("Authorization", "Bearer 56me538k6mevqf41tvjqe10nqj");
        },
        success: this.successHandler
    });
  },

  successHandler: function(data) {
      console.log(data);

      this.setState({
        retries: data.retries,
        email: data.email,
        phone: data.phone,
        onScreen: data.onScreen,
        threshold: data.threshold
      });
      this.forceUpdate();
  },
      
  handleRetriesChange: function(value){
    this.setState({
      retries: value
    });
  },

    handleThresholdChange: function(value){
      this.setState({
        threshold: value
      });
    },

    handlePhoneChange: function(event){
      this.setState({
        phone: event.target.value
      });
    },


    handleOnScreenChange: function(event){
      this.setState({
        onScreen: event.target.value
      });
    },

    handleEmailChange: function(event){
      this.setState({
        email: event.target.value
      });
    },

  render: function() {
      return (
        <form role="form" method="PUT" onSubmit={this.handleEditAlerts}>
          <div className="form-group">
            <label>Select Notification Channels</label>
            <div className="checkbox">
              <label>
                <input type="checkbox" checked={this.state.email} onChange={this.handleEmailChange}/>Email</label>
            </div>
            <div className="checkbox">
              <label>
                <input type="checkbox" checked={this.state.onScreen} onChange={this.handleOnScreenChange}/>On-Screen</label>
            </div>
            <div className="checkbox">
              <label>
                <input type="checkbox" checked={this.state.phone} onChange={this.handlePhoneChange}/>Phone</label>
            </div>
          </div>
          <div className="form-group">
            <label>Time Frame</label>
            <div className="row">
              <div className="col-lg-6">
                <select className="form-control" onChange={this.handleThresholdChange}>
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                </select>
              </div>
            </div>
          </div>
          <div className="form-group">
            <label>Repeats</label>
            <div className="row">
              <div className="col-lg-6">
                <select className="form-control" onChange={this.handleRetriesChange}>
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                </select>
              </div>
              <div className="col-lg-6">Times</div>
            </div>
          </div>

          <div className="modal-footer">
            <button className="btn btn-default" data-dismiss="modal">Cancel</button>
            <button className="btn btn-primary" type="submit">Save Changes</button>
          </div>
        </form>
    );
  }
});

ReactDOM.render(<EditAlerts/>,document.getElementById('edit-alerts-settings'))