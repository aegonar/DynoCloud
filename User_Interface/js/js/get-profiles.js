var React = require('react');
var ReactDOM = require('react-dom');

var ProfilesList = React.createClass({
    render: function() {
      var profiles = this.props.data.map(function (profile){
        return (
          <div>
            <h1>{profile.name}</h1>
          </div>
        );
      });
      return (
        <div>
          {profiles}
        </div>
      );
    }
  });

var ProfilesSelect = React.createClass({
    getInitialState: function(){
      return {
        data: {
          profiles:[]
        }
      };
    },
    getProfiles: function(){
      // mock ajax operation
      var url = 'dynocare.xyz/api/profiles';

      jQuery.ajax({
        url: url,
        dataType: 'json',
        success: function(data){
          this.setState({
              data: data
          });
        }.bind(this),
        error: function(xhr, status, err){
          console.error(url, status, err.toString());
        }.bind(this)
      });
      //var success = function(){
      //   var data = {
      //     comments : [
      //         { author : 'Mark Twein' },
      //         { author : 'Ernest Hemingway' },
      //         { author : 'Lewis Carroll' }
      //     ]  
      //   };
      //   this.setState( {data: data} );
      //}.bind(this);
      //setTimeout(success, 100);
    /*
      $.ajax({
        url: this.props.url,
        dataType: 'json',
        success: function(data){
          this.setState({data: data});
        }.bind(this),
        error: function(xhr, status, err){
          console.error(url, status, err.toString());
        }.bind(this)
      });*/
    },
  componentWillMount: function(){
      this.getProfiles();
  },
  render: function() {
    return (
      <div>
          {/*this.state.data.comments*/}
          {<ProfilesList data={this.state.data.profiles}/>}
      </div>
    );
  }
});

  React.renderComponent(
    <CommentBox />,
    document.getElementById('content')
  );