parameterMap ={
"Bernoulli":["p"],
"Geometric":["p"],
"Binomial":["n","p"],
"Hypergeometric":["n","N","m"],
"Poisson":["Lambda"],
"Negative Binomial":["r","p"],
"Normal":["Mean","Variance"],
"Exponential":["Lambda"],
"Uniform":["Alpha","Beta"]
};

window.onload=function(){
	showParameterInputs(document.getElementById('distributionType').value);
}
function submitForm(){
	console.log('here');
}

function showParameterInputs(value){
	parameters = parameterMap[value];
	console.log(parameters);
	var parameterWrapper = document.getElementById('parameterWrapper');
	while(parameterWrapper.firstChild) parameterWrapper.removeChild(parameterWrapper.firstChild);

	for(var i =0; i < parameters.length; i++){
		var parameter = parameters[i];
		var paramName = document.createTextNode(parameter + ": ");
		paramName.className = "parameterName";
		var input = document.createElement("input");
		input.type= "number";
		input.className = "parameterInput";
		input.name = parameter;

		var parameterInfo = document.createElement("div");
		parameterInfo.className = "parameterInfo";

		parameterInfo.appendChild(paramName);
		parameterInfo.appendChild(input);
		parameterWrapper.appendChild(parameterInfo);
	}
}