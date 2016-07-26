package com.schoolshieldchild.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.schoolshieldchild.presenter.WebServiceConnection;
import com.schoolshieldchild.view.services.UploadApplications;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    //region Constant Variables
    public static String DEVICE_TOKEN = "TOKEN";
    public static String STUDENT_ID = "STUDENT_ID";
    //endregion

    @Override
    public void onCreate() {
        super.onCreate();
        WebServiceConnection webServiceConnection = new WebServiceConnection();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public void startBackgroundServices() {

        if (!isMyServiceRunning(UploadApplications.class)) {
            startService(new Intent(getApplicationContext(), UploadApplications.class));
        }

    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}