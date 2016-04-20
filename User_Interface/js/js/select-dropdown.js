var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');

var BetterSelect = React.createClass({
  render: function() {
    if (this.props.valueLink) {
      return this.transferPropsTo(
        <select value={this.props.valueLink.value}
                valueLink={null} onChange={this.handleChange}>
          {this.props.children}
        </select>
      );
    } else {
      return this.transferPropsTo(
        <select onChange={this.handleChange}>
          {this.props.children}
        </select>
      );
    }
  },

  handleChange: function(e) {
    var selectedValue;
    if (this.props.multiple) {
      // We have to iterate the `options` elements
      // to figure out which ones are selected.
      selectedValue = [];
      var options = e.target.options;
      for (var i = 0, l = options.length; i < l; i++) {
        if (options[i].selected) {
          selectedValue.push(options[i].value);
        }
      }
    } else {
      selectedValue = e.target.value;
    }

    // Fire onChange manually if it exists since we overwrote it
    this.props.onChange && this.props.onChange(e);

    // Finally, manually take care of any valueLink passed
    if (this.props.valueLink) {
      this.props.valueLink.requestChange(selectedValue);
    }
  }
});

module.exports=BetterSelect;