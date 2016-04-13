var num = Math.round(100000 + Math.random());
var callbackName = "id_" + num;

var random_id = function(data){
	console.log(data);
}

url: this.props.url + "?callback=" + callbackName;