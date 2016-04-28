var React = require('react');
var ReactDOM = require('react-dom');

var Logout = React.createClass({
  componentDidMount: function(){


  },

  removeToken: function(){
    localStorage.removeItem('token');
  },

  render: function(){
    //redirect to index.html
  	return (
  	);
  }
});

ReactDOM.render(<Logout/>, document.getElementById('logout'))