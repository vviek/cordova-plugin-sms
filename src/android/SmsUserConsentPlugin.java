package com.cordova.plugin;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.*;


public class SmsUserConsentPlugin extends CordovaPlugin {
    private static final int REQ_USER_CONSENT = 200;
    private static final String START_LISTNING = "START_LISNING";
    private static final String STOP_LISTNING = "STOP_LISTNING";
    Activity mActivity;
    SmsBroadcastReceiver smsBroadcastReceiver;
    CallbackContext mCallbackContext;
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        this.mActivity = cordova.getActivity();

    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        mCallbackContext=callbackContext;



        Log.e("execute", ":" + action);
        if (START_LISTNING.equals(action)) {
            /*this.cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startSmsUserConsent();
                }
            });*/
            startSmsUserConsent();
            return true;
        }
        if (STOP_LISTNING.equals(action)) {
            startSmsUserConsent();
            return true;
        }
        return false;
    }

    private void startSmsUserConsent() {
        registerBroadcastReceiver();

        SmsRetrieverClient client = SmsRetriever.getClient(mActivity);
        //We can add sender phone number or leave it blank
        // I'm adding null here
        client.startSmsUserConsent(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
              //  Toast.makeText(mActivity, "On Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // Toast.makeText(mActivity, "On OnFailure", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        Log.e("Message Received","Received");
                       // mActivity.startActivityForResult(intent, REQ_USER_CONSENT);
                        SmsUserConsentPlugin.this.cordova.startActivityForResult((CordovaPlugin)SmsUserConsentPlugin.this, intent, REQ_USER_CONSENT);
                    }

                    @Override
                    public void onFailure() {

                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        mActivity.registerReceiver(smsBroadcastReceiver, intentFilter);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("OnActivity Result ",":Result");
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                //Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
                mCallbackContext.success(message);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    }

