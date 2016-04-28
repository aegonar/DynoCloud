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

        console.log(this.state.data);
    },

    render: function() {
        return (
          <div>
            <form role="form">
                <fieldset>
                    <div className="form-group">
                        <label>Name</label>
                        <input className="form-control" type="text" value={this.state.data.name} disabled/>
                    </div>
                    <div className="form-group">
                        <label>Last Name</label>
                        <input className="form-control" type="text" value={this.state.data.lastName} disabled/>
                    </div>
                    <div className="form-group">
                        <label>Username</label>
                        <input className="form-control" type="text" value={this.state.data.userName} disabled/>
                    </div>
                    <div className="form-group">
                        <label>Email</label>
                        <input className="form-control" type="text" value={this.state.data.email} disabled/>
                    </div>
                    <div className="form-group">
                        <label>Phone Number</label>
                        <input className="form-control" type="text" value={this.state.data.phone} disabled/>
                    </div>
                </fieldset>
              </form>
          </div>
        );
    }
});

ReactDOM.render(<GetUserData/>, document.getElementById('get-user-data'))