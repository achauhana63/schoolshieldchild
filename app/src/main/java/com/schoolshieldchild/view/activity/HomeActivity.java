package com.schoolshieldchild.view.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.schoolshieldchild.R;
import com.schoolshieldchild.app.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    static List<ResolveInfo> InstalledApps = new ArrayList<>();
    public static int app_value = 0;
    private static PackageManager pm;
    static HomeActivity instance;

    public HomeActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        instance = HomeActivity.this;
        setContentView(R.layout.activity_home);
        startServices();
    }


    private void startServices()
    {
        MyApplication.getInstance().startBackgroundServices();
    }
}
