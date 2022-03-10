var exec = require('cordova/exec');

exports.registerDevice = function (success, error) {
   exec(success, error, 'SmsUserConsentPlugin', 'START_LISNING');
};

