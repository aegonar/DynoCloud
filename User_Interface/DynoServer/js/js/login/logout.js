var React = require('react');
var ReactDOM = require('react-dom');

var Logout = React.createClass({
  removeToken: function(){
    localStorage.removeItem('token');
    window.location.replace('./index.html');
    window.history.forward();
  },

  render: function(){
  	return (
      <div>
        <a href="#" onClick={this.removeToken}><i className="fa fa-sign-out fa-fw"></i> Logout</a>
      </div>
    );
  }
});

ReactDOM.render(<Logout/>, document.getElementById('logout'))