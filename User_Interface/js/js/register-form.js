var React = require('react');
var ReactDOM = require('react-dom');

var RegisterUser = React.createClass({
  render: function() {
      return (
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
              <h4 class="modal-title">Register User Account</h4>
            </div>
            <div class="modal-body">
              <form role="form" method="POST">
                <div>
                  <input 
                    class="form-control" 
                    type="text" 
                    ref="name"
                    placeholder = "Name *"/>
                </div>
                <div>
                  <input 
                    class="form-control" 
                    type="text" 
                    ref="Last Name"
                    placeholder = "Last Name *"/>
                </div>
                <div>
                  <input 
                    class="form-control" 
                    type="text" 
                    ref="username"
                    placeholder = "Username *"/>
                </div>
                <div>
                  <input 
                    class="form-control" 
                    type="email" 
                    ref="email"
                    placeholder = "Email Address *"/>
                </div>
                <div>
                  <input 
                    class="form-control" 
                    type="email" 
                    ref="emailConfirm"
                    placeholder = "Confirm Email Address *"/>
                </div>
                <div>
                  <input 
                    class="form-control" 
                    type="password" 
                    ref="password"
                    placeholder = "Password *"/>
                </div>
                <div>
                  <input 
                    class="form-control" 
                    type="password" 
                    ref="passwordConfirm"
                    placeholder = "Confirm Password *"/>
                </div>
                <div>
                  <input 
                    class="form-control" 
                    type="number" 
                    ref="phone"
                    placeholder = "Phone Number"/>
                </div>
              </form>
              <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button class="btn btn-primary" type="submit"> Register</button>
              </div>
            </div>
          </div>
        </div>
      );
  }    
});
      
ReactDOM.render(<RegisterUser/>, document.getElementById('register-form'))