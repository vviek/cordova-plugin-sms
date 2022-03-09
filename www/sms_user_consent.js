
  function SmsUserConsentPlugin() {
  
  }
  //Send Data Plugin
  SmsUserConsentPlugin.prototype.registerDevice = function ( successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'SmsUserConsentPlugin', 'START_LISNING',[]);
  };
  SmsUserConsentPlugin.prototype.unregisterDevice = function ( successCallback, errorCallback) {
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
  
  
  