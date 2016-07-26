package com.schoolshieldchild.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.schoolshieldchild.R;
import com.schoolshieldchild.app.MyApplication;

public class HomeActivity extends AppCompatActivity {


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


    private void startServices() {
        MyApplication.getInstance().startBackgroundServices();
    }
}
