var React = require('react');
var ReactDOM = require('react-dom');
//var Header = require('./data/login-data');

var Parent = React.createClass({
    getInitialState: function() {
        return {
            childSelectValue: undefined,
        }
    },
    changeHandler: function(event) {
        this.setState({
            childSelectValue: event.target.value,
        });
    },

    render: function() {
        return (
            <div>
                <Select 
                    value={this.state.childSelectValue}
                    onChange={this.changeHandler}/>
            </div>
        )
    }
});

var Select = React.createClass({
    getInitialState: function() {
        return {
            options: []
        }
    },
    componentDidMount: function() {
        jQuery.ajax({
            url: 'http://dynocare.xyz/api/profiles',
            dataType: 'json',
            beforeSend: function (xhr) {
              xhr.setRequestHeader ("Authorization", "Bearer 56me538k6mevqf41tvjqe10nqj");
            },
            success: this.successHandler
        })
    },
    successHandler: function(data) {
        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            this.state.options.push(
                <option key={option.petProfileID} value={option.name}>{option.name}</option>
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
        );
    }
});

module.exports = Parent;