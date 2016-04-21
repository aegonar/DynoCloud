var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');
var header = "Bearer 56me538k6mevqf41tvjqe10nqj";


var EditAlerts = React.createClass({
  
  getInitialState: function(){
    return {
      userID: null,
      retries: "",
      email: false,
      phone: false,
      onScreen: false,
      threshold: ""
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
        xhr.setRequestHeader ("Authorization", header);
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
      
  handleRetriesChange: function(event){
    this.setState({
      retries: event.target.value
    },
      function(){
        this.setState({
          retries: this.state.retries
        });
      });
  },

  handleThresholdChange: function(event){
    this.setState({
      threshold: event.target.value
    },
    function(){
      this.setState({
        threshold: this.state.threshold
      });
    });
  },

  handlePhoneChange: function(event){
    this.setState({
      phone: event.target.checked
    },
      function(){
        this.setState({
          phone: this.state.phone
        });
      }
    );
  },


  handleOnScreenChange: function(event){
    this.setState({
      onScreen: event.target.checked
    },
      function(){
        this.setState({
          onScreen: this.state.onScreen
        });
      }
    );
  },

  handleEmailChange: function(event){
    this.setState({
      email: event.target.checked
    },
      function(){
        this.setState({
          email: this.state.email
        });
      }
    );
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
                <select className="form-control" selected={this.state.threshold} onChange={this.handleThresholdChange}>
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
                <select className="form-control" selected={this.state.retries} onChange={this.handleRetriesChange}>
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
 
ReactDOM.render(<EditAlerts />, document.getElementById('edit-alerts-settings'))