var React = require('react');
var ReactDOM = require('react-dom');

var GetMetrics = React.createClass({
	getInitialState: function() {
        return {
        	data: [],
        	modules: [],
        	enclosureNodeID: "",
        	centralNodeID: "",
        	hours: "",

        }
    },

    componentDidMount: function(){
    	jQuery.ajax({
            url: 'http://dynocare.xyz/api/module',
            dataType: 'json',
          beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
          },
          success: this.successHandler
        });
    },

    successHandler: function(data){
        for (var i = 0; i < data.length; i++) {
            var option = data[i];
            this.state.modules.push(
                <option key={option.centralNodeID+"."+option.enclosureNodeID} value={option.name}>{option.name}</option>
            );
            this.state.data.push(option);
        }
        this.setState({
            selected: this.state.data[0],
            hours: "5"
        });
        console.log(this.state.hours);
        console.log(this.state.selected);
        this.forceUpdate();
        this.getMetrics();
    },

	getMetrics: function(){
		jQuery.ajax({
            url: 'http://dynocare.xyz/api/metrics/' + this.state.selected.centralNodeID + '/' + this.state.selected.enclosureNodeID + '/' + this.state.hours,
            dataType: 'json',
            beforeSend: function(xhr) {
            if (localStorage.getItem('token')) {
              xhr.setRequestHeader('Authorization',
                    'Bearer ' + localStorage.getItem('token'));
            }
      	},
        	success: this.generateMetrics
    	})

	},

	generateMetrics: function(data){
	 
        if (typeof Morris != 'undefined') {
           
            Morris.Line({
                element: 'temperature',
                data: data,
                xkey: 'dateTime',
                ykeys: ['TEMP'],
                labels: ['Temperature'],
                parseTime: true,
                lineColors: ['#242d3c']
            });

            Morris.Line({
                element: 'humidity',
                data: data,
                xkey: 'dateTime',
                ykeys: ['RH', 'HUM_STATUS'],
                labels: ['Humidity','Humidifier'],
                parseTime: true,
                lineColors: ['#242d3c']
            });

            Morris.Line({
                element: 'load',
                data: data,
                xkey: 'dateTime',
                ykeys: ['HEAT_LOAD'],
                labels: ['Load'],
                parseTime: true,
                lineColors: ['#242d3c']
            });
        }
	},

	getRandomInt: function(min, max) {
		return Math.floor(Math.random() * (max - min + 1)) + min;
	},

	handleHourChange: function(event){
		this.setState({
			hours: event.target.value
		});
		this.getMetrics();
	},

	handleChange: function(event) {
        for(var i = 0; i < this.state.data.length; i++){
          	var option = this.state.data[i];
          	if(event.target.value == option.name){
            	this.setState({
                	selected: option,
            	},
            		function(){
            			this.setState({
            				selected: this.state.selected
            			});
            		}
            	);
            	this.forceUpdate();
            	this.getMetrics();
          	}
        }
    },

	render: function(){
		return (
			<div>
				<div className="row">
					<div div className="form-group" {...this.props}>
		                <select onChange={this.handleChange}>
		                    {this.state.modules}
		                </select>
		                <input type="text" placeholder="Hours *" value={this.state.hours} onChange={this.handleHourChange}></input> Hours
		            </div>
				</div>

				<div className="row">
		          <div className="col-lg-12">
		            <div className="panel panel-default">
		              <div className="panel-heading">Humidity vs. Time</div>
		              <div className="panel-body">
		                <div className="flot-chart">
		                  <div className="flot-chart-content" id="humidity"></div>
		                </div>
		              </div>
		            </div>
		          </div>
		        </div>

		        <div className="row">
		          <div className="col-lg-12">
		            <div className="panel panel-default">
		              <div className="panel-heading">Temperature vs. Time</div>
		              <div className="panel-body">
		                <div className="flot-chart">
		                  <div className="flot-chart-content" id="temperature"></div>
		                </div>
		              </div>
		            </div>
		          </div>
		        </div>

		        <div className="row">
		          <div className="col-lg-12">
		            <div className="panel panel-default">
		              <div className="panel-heading">Load vs Time</div>
		              <div className="panel-body">
		                <div className="flot-chart">
		                  <div className="flot-chart-content" id="load"></div>
		                </div>
		              </div>
		            </div>
		          </div>
		        </div>
	        </div>
		);
	}

});

ReactDOM.render(<GetMetrics/>, document.getElementById('metrics'))