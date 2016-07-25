package com.schoolshieldchild.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.schoolshieldchild.R;
import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;
import com.schoolshieldchild.view.custom_controls.TextView_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.textView_AgreeAndContinue)
    TextView_Regular textView_AgreeAndContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        if (!SharedPref.getString(MyApplication.STUDENT_ID).equalsIgnoreCase("")) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @OnClick(R.id.textView_AgreeAndContinue)
    public void AgreeAndContinueClick() {
        Intent intent = new Intent(this, TutorialActivity.class);
        startActivity(intent);
        finish();
    }
}
