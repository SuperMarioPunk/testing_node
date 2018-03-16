var paddingSupport = require('./paddingSupport');
//constantes
const NUM_FILLER = 0;
const ALFA_FILLER = ' ';
const NULL = '';
//numericos alineados a la derecha con ceros a la izquierda
module.exports.numeric = function (input, length) { 
    //console.log('paddinUtils','numeric',input,length);
    if (input == null || input == undefined) input = NULL;
    if (length == null || length == undefined) length = 1;
    return paddingSupport.padStart(input.toString(),length, NUM_FILLER); 
};
//alfanumericos alineados a la izquierda con espacios a la derecha
module.exports.alpha = function (input, length) { 
    //console.log('paddinUtils', 'alpha', input, length);
    if (input == null || input == undefined) input = NULL;
    if (length == null || length == undefined) length = 1;
    return paddingSupport.padEnd(input.toString(),length, ALFA_FILLER); 
};
