

var RegisterForm = React.createClass({ 
   	render: function() {
     	return (
            React.createElement('input', {
				type: 'text',
				placeholder: 'Testing',
			}),
 		)
     },

});
 
 ReactDOM.render(RegisterForm, document.getElementById('register-form'))