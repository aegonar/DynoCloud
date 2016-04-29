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
                <label>Name</label>
                <div>{this.state.data.name}</div>
            </div>
            <div className="form-group">
                <label>Last Name</label>
                <div>{this.state.data.lastName}</div>
            </div>
            <div className="form-group">
                <label>Username</label>
                <div>{this.state.data.userName}</div>
            </div>
            <div className="form-group">
                <label>Email</label>
                <div>{this.state.data.email}</div>
            </div>
            <div className="form-group">
                <label>Phone Number</label>
                <div>{this.state.data.phone}</div>
            </div>
          </div>
        );
    }
});

ReactDOM.render(<GetUserData/>,document.getElementById('get-user-data'))