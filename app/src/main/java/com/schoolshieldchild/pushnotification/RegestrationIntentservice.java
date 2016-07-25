package com.schoolshieldchild.pushnotification;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.schoolshieldchild.R;
import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;

import java.io.IOException;


public class RegestrationIntentservice extends IntentService {
    private static final String name = "Regservice";

    public RegestrationIntentservice() {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
        try {
            String token = instanceID.getToken(getResources().getString(R.string.sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            SharedPref.setString(MyApplication.DEVICE_TOKEN, token);
            Log.d("Token============", token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
