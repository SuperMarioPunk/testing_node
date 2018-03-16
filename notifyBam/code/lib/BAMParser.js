'use strict';
const schema = require('./schemaData.json');
var Ajv = require('ajv');
var ajv = new Ajv(); // options can be passed, e.g. {allErrors: true}
var validate = ajv.compile(schema);
var paddingUtils = require('./paddingUtils');

module.exports.format = function(inputText,callback) {
	//console.log(inputText);
	try {
		if (!validate(inputText)) throw validate.errors.map(e => e.message ).join('');
		var data = Object.keys(schema.properties).map((k, i) => {
			var value = '';
			var attr = schema.properties[k];
			var len = getLength(attr, k);
			//verificamos si la entrada cuenta con el atributo actual
			if (attr['default']) value = attr['default']
			else if (inputText.hasOwnProperty(k) && inputText[k] != null && inputText[k] != undefined) 
				value = inputText[k];
			else if ("scaCode" == k) {
				value = parseInt(inputText['acctBal']) < 0 ? '-' : '+';
			} ;
			//retornamos el valor decorado.
			return attr.type === 'number'  ? paddingUtils.numeric(Math.abs(value), len) : paddingUtils.alpha(value, len);
		}).join('');
		//console.log(data);
		return callback(null, data);
	}
	catch(err){
		return callback(err);
	}	
}

module.exports.parser = function(inputText,callback){
	try {
		var attr = {};
		var ke = 0;
		var datos = Object.keys(schema.properties).map((k, i) => {
			attr = schema.properties[k];
			ke = k;
			var l = getLength(attr, ke);
			var dato = inputText.slice(0, l);
			inputText = inputText.slice(l);
			return dato;
		});
		if (inputText.length > 0) throw 'Error parser maxlength';
		if (getLength(attr,ke) > datos[datos.length - 1].length) throw 'Error parser less data.';
		callback(null, datos);
	}
	catch (err) {
		return callback(err);
	}
}

function getLength(attr,k) {
	var len = 0;
	if (["f0", "f1", "f10", "f11", "f2", "f3", "f4", "f5", "f59", "f60", "f6", "f7", "f8","f9", "trnTypeValue"].indexOf(k) >= 0 && attr.type === 'number'){
		len = attr.maxLength;
	}
	else if (attr.type === 'number') len = attr.maximum.toString().length
	else len = attr.maxLength;
	return  len;
}
