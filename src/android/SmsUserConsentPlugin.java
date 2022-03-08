package com.cordova.plugin;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import de.idnow.core.IDnowConfig;
import de.idnow.core.IDnowResult;
import de.idnow.core.IDnowSDK;

public class SmsUserConsentPlugin extends CordovaPlugin implements IDnowSDK.IDnowResultListener {
    private static String TAG = IdNowPlugin.class.getSimpleName();
    private IDnowSDK idnowSdk;
    private static final String  START_IDENT="START_IDENT";
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        IDnowConfig idnowConfig = IDnowConfig.Builder.getInstance()
                .withLanguage("en") // this line is not necessary, please see below
                .build();

        idnowSdk = IDnowSDK.getInstance();
        idnowSdk.initialize(cordova.getActivity(), idnowConfig);

    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {

        if (START_IDENT.equals(action)) {
            this.cordova.getActivity().runOnUiThread(new Runnable() {


                                                         @Override
                                                         public void run() {
                                                             try {
                                                                 startIdent(callbackContext,args.getString(0));
                                                             } catch (JSONException e) {
                                                                 e.printStackTrace();
                                                             }
                                                         }
                                                     });



            return true;
        }

return false;
    }

    private void startIdent(CallbackContext callbackContext, String token) {
        idnowSdk.startIdent(token, this);

        //idnowSdk.startIdent("PNW-JNTUR", this);
        callbackContext.success();
    }

    @Override
    public void onIdentResult(IDnowResult iDnowResult) {
        if (iDnowResult.getIDnowStatusCode() == IDnowResult.IDnowStatusCode.FINISHED) {
            Log.d(TAG, "Finished");
        } else if (iDnowResult.getIDnowStatusCode() == IDnowResult.IDnowStatusCode.CANCELLED) {
            Log.d(TAG, "Cancelled");
        } else if (iDnowResult.getIDnowStatusCode() == IDnowResult.IDnowStatusCode.ERROR) {
            Log.d(TAG, "Error: " + iDnowResult.getMessage());
        }
    }
}
