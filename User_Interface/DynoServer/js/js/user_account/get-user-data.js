var React = require('react');
var ReactDOM = require('react-dom');


var GetUserData = React.createClass({
    getInitialState: function() {
        return {
            data: {},
        }
    },
    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/user',
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
        this.setState({
            data: data,
        });
    },

    render: function() {
        return (
          <div>
            <div className="form-group">
                <label><h4>Name</h4></label>
                <div>{this.state.data.name}</div>
            </div>
            <div className="form-group">
                <label><h4>Last Name</h4></label>
                <div>{this.state.data.lastName}</div>
            </div>
            <div className="form-group">
                <label><h4>Username</h4></label>
                <div>{this.state.data.userName}</div>
            </div>
            <div className="form-group">
                <label><h4>Email</h4></label>
                <div>{this.state.data.email}</div>
            </div>
            <div className="form-group">
                <label><h4>Phone Number</h4></label>
                <div>{this.state.data.phone}</div>
            </div>
          </div>
        );
    }
});

ReactDOM.render(<GetUserData/>,document.getElementById('get-user-data'))