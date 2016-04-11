/*
* Data
*/
var users = [
	{key: "1", name: "Keysha", lastname: "Gonzalez", username: "keyminel", email: "keysha.gonzalez1@upr.edu", password: "1234", phone: "7872084122"},
]

var newUser = {
	name: "", 
	lastname: "", 
	username: "", 
	email: "", 
	password: "",
	phone: ""
}

/*
* Form Components
*/
var RegisterForm = React.createClass({
	propTypes: {
		value: React.PropTypes.object.isRequired,
		onChange: React.PropTypes.func.isRequired,
	},

  	render: function() {
    	return (
    		React.createElement('form', {
    			className: 'RegisterForm',
    			method: 'POST',
    		},
    			React.createElement('input', {
    				type: 'text', 
    				placeholder: 'Name (required)', 
    				value: this.props.value.name,
    			}),
				React.createElement('input', {
					type: 'text', 
					placeholder: 'Last Name (required)', 
					value: this.props.value.lastname,
				}),
				React.createElement('input', {
					type: 'text', 
					placeholder: 'Username (required)', 
					value: this.props.value.username,
				}),
				React.createElement('input', {
					type: 'email',
					placeholder: 'Email (required)',
					value: this.props.value.email,
				}),
				React.createElement('input', {
					type: 'email',
					placeholder: 'Confirm Email (required)',
					value: this.props.value.emailConf,
				}),
				React.createElement('input', {
					type: 'password',
					placeholder: 'Password (required)',
					value: this.props.value.pass,
				}),
				React.createElement('input', {
					type: 'password',
					placeholder: 'Confirm Password (required)',
					value: this.props.value.passConf,
				}),
				React.createElement('input', {
					type: 'number',
					placeholder: 'ex. 999999999',
					pattern='[0-9]*'
					value: this.props.value.phone,
				}),
				React.createElement('input', {
					type: 'number',
					placeholder: 'ex. 999999999',
					pattern='[0-9]*'
					value: this.props.value.phoneConf,
				}),
				React.createElement('button', {
					type: 'submit'
				})
			)
		)
  	},
});

ReactDOM.render(RegisterForm, document.getElementById('register-form'))