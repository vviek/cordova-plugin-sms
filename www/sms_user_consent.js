
function SmsUserConsentPlugin() {

}
//Send Data Plugin
SmsUserConsentPlugin.prototype.sendData = function (dataToSend, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, 'IdNowPlugin', 'START_IDENT',[dataToSend]);
};

SmsUserConsentPlugin.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.smsUserConsentPlugin = new SmsUserConsentPlugin();
  return window.plugins.smsUserConsentPlugin;
};

cordova.addConstructor(SmsUserConsentPlugin.install);
