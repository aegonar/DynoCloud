var React = require('react');
var ReactDOM = require('react-dom');
var classNames = require('classnames');
var _ = require('underscore');

var ValidatePassword = React.createClass({

  getInitialState: function(){
    return {
      value: null,
      minCharacters: this.props.minCharacters,
      requireCapitals: this.props.requireCapitals,
      requireNumbers: this.props.requireNumbers,
      name: this.props.name
    };
  },

  render: function(){ 
    var validatorClass = classNames({
      'password_validator':   true,
      'visible':              this.props.visible,
      'invisible':            !this.props.visible
    });

    var validatorTitle;

    if(this.props.valid) {
      validatorTitle = 
        <h4 className="form-group has-success">
          {this.props.name} Your password is valid.
        </h4>
    } else {
      validatorTitle = 
        <h4 className="form-group has-error">
          {this.props.name} Requirements
        </h4>
    }

    return (
      <div className={validatorClass}>
        <div>
          {validatorTitle}
          <ul>
            <li className={classNames({'valid': this.props.validData.minChars})}> 
              <i className="fa fa-3x fa-check-circle fa-fw text-success"></i>
              <i className="fa fa-3x fa-close fa-fw text-danger"></i>
              <span>{this.state.minCharacters} characters minimum</span>
            </li>

            <li className={classNames({'valid': this.props.validData.capitalLetters})}> 
              <i className="fa fa-3x fa-check-circle fa-fw text-success"></i>
              <i className="fa fa-3x fa-close fa-fw text-danger"></i>
              <span>Contains at least {this.state.requireCapitals} capital letter</span>
            </li>

            <li className={classNames({'valid': this.props.validData.numbers})}> 
              <i className="fa fa-3x fa-check-circle fa-fw text-success"></i>
              <i className="fa fa-3x fa-close fa-fw text-danger"></i>
              <span>Contains at least {this.state.requireNumbers} number</span>
            </li>
          </ul>
        </div>
      </div>
    );
  }
});

module.exports = ValidatePassword;