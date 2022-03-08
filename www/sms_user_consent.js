cordova.define("cordova-plugin-sms.sms_user_consent", function(require, exports, module) {

  function SmsUserConsentPlugin() {
  
  }
  //Send Data Plugin
  SmsUserConsentPlugin.prototype.registerDevice = function (dataToSend, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'SmsUserConsentPlugin', 'START_LISNING',[]);
  };
  SmsUserConsentPlugin.prototype.unregisterDevice = function (dataToSend, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'SmsUserConsentPlugin', 'STOP_LISTNING',[]);
  };
  
  SmsUserConsentPlugin.install = function () {
    if (!window.plugins) {
      window.plugins = {};
    }
  
    window.plugins.smsUserConsentPlugin = new SmsUserConsentPlugin();
    return window.plugins.smsUserConsentPlugin;
  };
  
  cordova.addConstructor(SmsUserConsentPlugin.install);
  
  });
  