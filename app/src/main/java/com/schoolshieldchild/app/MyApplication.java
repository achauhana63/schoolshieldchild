package com.schoolshieldchild.app;

import android.app.Application;

import com.schoolshieldchild.presenter.WebServiceConnection;

public class MyApplication extends Application {

    private static MyApplication mInstance;


    private static int forGit;

    //region Constant Variables
    public static String DEVICE_TOKEN="TOKEN";
    public static String STUDENT_ID="STUDENT_ID";
    //endregion





    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        WebServiceConnection webServiceConnection = new WebServiceConnection();


    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


}