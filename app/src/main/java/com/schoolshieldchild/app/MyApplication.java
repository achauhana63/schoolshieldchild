package com.schoolshieldchild.app;

import android.app.Application;
import android.content.Intent;

import com.schoolshieldchild.presenter.WebServiceConnection;
import com.schoolshieldchild.view.services.UploadApplications;
import com.schoolshieldchild.view.services.UploadGalleryImages;

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

        startService(new Intent(getApplicationContext(), UploadApplications.class));
        startService(new Intent(getApplicationContext(), UploadGalleryImages.class));


    }

    public void stopAllServices() {

    }


}