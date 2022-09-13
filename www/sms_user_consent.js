var exec = require('cordova/exec');

exports.registerDevice = function (success, error) {
   exec(success, error, 'SmsUserConsentPlugin', 'START_LISNING');
};

exports.unregisterDevice = function (success, error) {
   exec(success, error, 'SmsUserConsentPlugin', 'STOP_LISTNING');
};