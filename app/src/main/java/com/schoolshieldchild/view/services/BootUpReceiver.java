package com.schoolshieldchild.view.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
            MyApplication.getInstance().startBackgroundServices();
        }
    }
}