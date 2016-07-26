package com.schoolshieldchild.view.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;
import com.schoolshieldchild.model.applicationprp.ApplicationPrp;
import com.schoolshieldchild.view.database.DataBaseHandler;

import java.util.ArrayList;
import java.util.List;

public class UninstallIntentReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            {
                String PACKAGE = intent.getData().toString().substring(8);
                if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
                    if (!PACKAGE.equalsIgnoreCase("com.android.packageinstaller")) {
                        DataBaseHandler dataBaseHandler = new DataBaseHandler(context);
                        dataBaseHandler.deleteApplication(PACKAGE);
                    }
                }
            }
        }
        else
        {

        }

    }
}