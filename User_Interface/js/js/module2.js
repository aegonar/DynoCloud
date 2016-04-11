
var RegisterUser = React.createClass({
  getInitialState: function () {
    return {
        name: null,
        lastname: null,
        username: null,
        email: null,
        confirmEmail: null,
        password: null,
        confirmPassword: null,
        phone: null,
        forbiddenWords: ["password", "user", "username", "12345678"]
      }
    },

  /* Verifying if password field is not empty. */
  passwordHandling: function (event) {
      if(!_.isEmpty(this.state.confirmPassword)){
          this.refs.passwordConfirm.isValid();
      }

      this.refs.passwordConfirm.hideError();
      this.setState({
          password: event.target.value
      });
  },

  confirmPasswordHandling: function (event) {
      this.setState({
          confirmPassword: event.target.value
      });
  },

  register: function (e) {
      e.preventDefault();

      var canProceed = this.validateEmail(this.state.email) 
          && this.validateEmail(this.state.emailConfirm)
          && !_.isEmpty(this.state.name)
          && !_.isEmpty(this.state.lastname)
          && !_.isEmpty(this.state.username)
          && this.refs.password.isValid()
          && this.refs.passwordConfirm.isValid();

      if(canProceed) {
        var data = {
          name: this.state.name,
          lastname: this.state.lastname,
          username: this.state.username,
          email: this.state.email,
          phone: this.state.phone,
        }
        alert('Thanks.');
      } else {
        this.refs.name.isValid();
        this.refs.lastname.isValid();
        this.refs.username.isValid();
        this.refs.email.isValid();
        this.refs.emailConfirm.isValid();
        this.refs.password.isValid();
        this.refs.passwordConfirm.isValid();
        this.refs.phone.isValid();
      }
    },

    isConfirmedPassword: function (event) {
      return (event == this.state.password)
    },

    handleNameInput: function(event) {
      this.setName({
        name: event.target.value
      })
    },

     handleLastNameInput: function(event) {
      this.setLastName({
        lastname: event.target.value
      })
    },

     handleUsernameInput: function(event) {
      this.setUsername({
        username: event.target.value
      })
    },

    handleEmailInput: function(event){
      this.setState({
        email: event.target.value
      });
    },

    validateEmail: function (event) {
      // regex from http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
      var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(event);
    },

    isEmpty: function (value) {
      return !_.isEmpty(value);
    },

     handlePhoneInput: function(event) {
      this.setPhone({
        phone: event.target.value
      })
    },

    render: function() {
      return (
        <div>
          <input 
            class="form-control" 
            type="text" 
            ref="name"
            placeholder = "Name *"
            defaultValue={this.state.name} 
            validate={this.isEmpty}
            value={this.state.name}
            onChange={this.handleNameInput} 
            emptyMessage="Email can't be empty" />
        </div>
      );
    }
});
        
ReactDOM.render(<RegisterUser/>, document.getElementById('register-form'))