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
        <h4>
          {this.props.name} Your password is valid.
        </h4>
    } else {
      validatorTitle = 
        <h4>
          {this.props.name} Your password is invalid.
        </h4>
    }

    return (
      <div className={validatorClass}>
        <div>

          {validatorTitle}

          <ul>
        
            <li className={classNames({'valid': this.props.validData.minChars})}> 
              <i></i>
              <i></i>
              <span>{this.state.minCharacters} characters minimum</span>
            </li>

            <li className={classNames({'valid': this.props.validData.capitalLetters})}> 
              <i> </i>
              <i> </i>
              <span>Contains at least {this.state.requireCapitals} capital letter</span>
            </li>

            <li className={classNames({'valid': this.props.validData.numbers})}> 
              <i> </i>
              <i> </i>
              <span>Contains at least {this.state.requireNumbers} number</span>
            </li>
          </ul>
        </div>
      </div>
    );
  }
});

module.exports = ValidatePassword;