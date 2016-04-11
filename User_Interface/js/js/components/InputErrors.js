var React = require('react');
var ReactDOM = require('react-dom');
var classNames = require('classnames');
var _ = require('underscore');

var InputErrors = React.createClass({

  getInitialState: function(){
    return {
      message: 'Invalid Input.'
    };
  },

  render: function(){ 
    var errorClass = classNames({
      'error_container':   true,
      'visible':           this.props.visible,
      'invisible':         !this.props.visible
    });

    return (
      <div className={errorClass}>
        <span>{this.props.errorMessage}</span>
      </div>
    )
  }

})

module.exports = InputErrors;