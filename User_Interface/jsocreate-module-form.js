/*

validate fields
write data to JSON (then AJAX)
redirect to overview.html
add this form to Add Module Select Pet Profile (custom)
add picture option

*/

var React = require('react');
var ReactDOM = require('react-dom');
var _ = require('underscore');

var PROFILES = require('./components/profiles.js');

var CreateModule = React.createClass({
  propTypes: {
    name: React.PropTypes.string
  },
  
  getInitialState: function () {
    return {
      options: {PROFILES},
      id: 0,
      modulename: '',
      profilename: '',
      matchPos: 'any',
      matchValue: true,
      matchLabel: true,
      value: null,
      multi: false
    };
  },

  onChangeMatchStart: function (event) {
    this.setState({
      matchPos: event.target.checked ? 'start' : 'any'
    });
  },

  onChangeMatchValue: function(event) {
    this.setState({
      matchValue: event.target.checked
    });
  },

  onChangeMatchName: function(event) {
    this.setState({
      matchName: event.target.checked
    });
  },

  onChange: function (value) {
    this.setState({ value });
    console.log('Numeric Select value changed to', value);
  },

  onChangeMulti: function(event) {
    this.setState({
      multi: event.target.checked
    });
  },



  isEmpty: function (value) {
    return !_.isEmpty(value);
  },

  handleModuleNameInput: function(event){
    this.setState({
      modulename: event.target.value
    });
  },

  handleModuleCreatedSuccess: function(data, status, xhr){
    this.setState(data);
    console.log(data); //verifying the data was correct
  },

  getProfiles: function(){

  },

  handleModuleCreateSubmit: function (e) {
    e.preventDefault();
      var canProceed = !_.isEmpty(this.state.modulename); 

    if(canProceed) {
      var modData = {        
        id: this.state.id + 1,
        modulename: this.state.modulename,
        petprofile: this.state.petprofile
      };

      this.handleModuleCreatedSuccess(modData);

      /* Open MCU config and peripherals modal*/
      alert('New module created.');
    } 
    else {
      !_.isEmpty(this.state.modulename);
    }
  },

  render: function() {
    var matchProp = 'any';
    if (this.state.matchName && !this.state.matchValue) {
      matchProp = 'name';
    }
    if (!this.state.matchName && this.state.matchValue) {
      matchProp = 'value';
    }
    return (
      <form role="form" onSubmit={this.handleModuleCreateSubmit} method="POST">

        <div className="form-group">
          <label>Module Name</label>
          <input 
            className="form-control" 
            type="text" 
            ref="module"
            validate={this.isEmpty}
            value={this.state.modulename}
            onChange={this.HandleModuleNameInput}/>
        </div>

        <div className="form-group">
          <label>Select Pet Profile</label>
          </>

        </div>

        <div className="modal-footer">
            <a className="btn btn-default" data-dismiss="modal">Cancel</a>
            <a className="btn btn-primary" data-toggle="modal" data-target="#connecting">Save changes</a>
          </div>
        </form>
    );
  }
});

ReactDOM.render(<CreateModule/>, document.getElementById('create-module-form'))