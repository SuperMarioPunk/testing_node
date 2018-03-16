'use strict';

const parser = require('./lib/BAMParser');
const AWS = require('aws-sdk');
const NULL = '';

const schema = require('./models/request.json');
var Ajv = require('ajv');
var ajv = new Ajv();
var validate = ajv.compile(schema);
var moment = require('moment');

exports.notifyBam = (event, context, callback) => {
  
    try {
        var rq = event.body ? JSON.parse(JSON.stringify(event.body)) : event;
        console.log('Request input:', rq);
      const MQCON = verifyEnv('MQCON', true);
    const QUEUE = verifyEnv('QUEUE', true);
    //var sns = sns || new AWS.SNS({ region: verifyEnv('AWS_REGION') });
     var lambda = lambda || new AWS.Lambda({ region: verifyEnv('AWS_REGION') });
    if (!validate(rq)) throw validate.errors.map(e => e.message).join('');
    var rqData = inputFormat(rq);
    console.log('Request data', rqData);
    parser.format(rqData, (err, data) => {
      if (err) throw err;
      //console.log("Before: ",data);
      var snsTextData = JSON.stringify({ "destUrl": QUEUE, "text":  data  });
      //onsole.log("after: ", snsTextData);

      console.log('Sending:', snsTextData);
      var publishParams = {
        FunctionName: MQCON, 
        LogType: "Tail", 
        InvocationType: "Event",
        Payload: snsTextData
      };
      lambda.invoke(publishParams, (err, snsData) => {
        var response = {
          "rqUID": rq.rqUID,
          "status": {
            "statusCode": err ? err.statusCode : 200,
            "statusDesc": err ? err.code : "Ok"
          },
          "message": err ? err.message : snsData
        };
        callback(null, response);
      });

    });
  } catch (err) {
    //console.error(err);
    var response = {
      "rqUID": rq.rqUID,
      "status": {
        "statusCode": 400,
        "statusDesc": "Bad Request",
      },
      "message": err,
    };
    callback(null, response);
  }


};

function inputFormat(params) {
  var fecha = moment(params.dueDate, moment.ISO_8601);
  return JSON.parse(JSON.stringify({
    "seqNum": params.msgRqHdr.credentialsRsHdr.seqNum,
    "trnDate": fecha.format('MMDDYY'),
    "fromAcctId": params.alertInfo.fromAcctRef.acctRec.acctId,
    "curAmt": params.alertInfo.curAmt.amt,
    "curCode": params.alertInfo.curAmt.curCode || '',
    "toAcctId": params.alertInfo.toAcctRef ? params.alertInfo.toAcctRef.acctRec.acctId : '',
    "posLocation": params.msgRqHdr.contextRqHdr.pointOfServiceData.posLocation,
    "memo": params.alertInfo.memo,
    "agentIdent": params.msgRqHdr.contextRqHdr.pointOfServiceData.posAgent ? params.msgRqHdr.contextRqHdr.pointOfServiceData.posAgent.agentIdent : '',
    "acctBal": params.alertInfo.fromAcctRef.acctRec.acctInfo ? params.alertInfo.fromAcctRef.acctRec.acctInfo.acctBal : '',
    "trnTypeValue": params.alertInfo.trnType ? params.alertInfo.trnType.trnTypeValue.toString() : '' ,
    "scaCode": '+' ,
    "scaDate": fecha.format('MM/DD/YY'),
    "trnTime": fecha.format('HHmmss')
  }));

}

function verifyEnv(envVar, req) {
  if (req && (process.env[envVar] === '' || process.env[envVar] === undefined)) throw envVar + ' is required.';
  return process.env[envVar];
}