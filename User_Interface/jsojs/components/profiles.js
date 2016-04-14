var React = require('react');
var ReactDOM = require('react-dom');
var OPTIONS = require('../data/default-profiles.js');

var Parent = React.createClass({
    getInitialState: function() {
        return {
            childSelectValue: undefined,
            options: []
        }
    },
    changeHandler: function(e) {
        this.setState({
            childSelectValue: e.target.value
        });
    },

    render: function() {
        return (
            <div>
                <Select 
                    options={OPTIONS}
                    value={this.state.childSelectValue}
                    onChange={this.changeHandler}/>
            </div>
        )
    }
});

var Select = React.createClass({
    propTypes: {
        /*url: React.PropTypes.string.isRequired,*/
        options: React.PropTypes.array
    },
    getInitialState: function() {
        return {
            options: []
        }
    },
    componentDidMount: function() {
        // get your data
        this.successHandler(this.props.options);
        /*$.ajax({
            options: this.props.options,
            success: this.successHandler
        })*/
    },
    successHandler: function(data) {
        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            this.state.options.push(
                <option key={option.id} value={option.value}>{option.name}</option>
            );
        }
        this.forceUpdate();
    },

    render: function() {
        return (
            <div {...this.props}>
                <select>
                    {this.state.options}
                </select>
            </div>
        )
    }
});

module.exports = Parent;