package com.schoolshieldchild.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.schoolshieldchild.R;
import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.Consts_Variable;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;
import com.schoolshieldchild.controller.utils.DialogManager;
import com.schoolshieldchild.presenter.WebServiceResult;
import com.schoolshieldchild.pushnotification.RegestrationIntentservice;
import com.schoolshieldchild.view.custom_controls.Button_Regular;
import com.schoolshieldchild.view.custom_controls.EditText_Regular;
import com.schoolshieldchild.view.custom_controls.TextView_Regular;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.button_confirm)
    Button_Regular button_confirm;
    @BindView(R.id.editText_childpin)
    EditText_Regular editText_childpin;
    @BindView(R.id.textView_HowToGetPin)
    TextView_Regular textView_HowToGetPin;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activity = LoginActivity.this;
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        showConfirmButton();
        Intent intent = new Intent(this, RegestrationIntentservice.class);
        startService(intent);
    }

    public void showConfirmButton() {
        editText_childpin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText_childpin.getText().toString().length() >= 4) {
                    button_confirm.setVisibility(View.VISIBLE);
                } else button_confirm.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @OnClick(R.id.button_confirm)
    public void setConfirmClick() {

        if (!SharedPref.getString(MyApplication.DEVICE_TOKEN).equalsIgnoreCase("")) {
            DialogManager.startProgressDialog(this);
            WebServiceResult.ChildLogin(editText_childpin.getText().toString(), SharedPref.getString(MyApplication.DEVICE_TOKEN), Consts_Variable.deviceType);
        } else
            Toast.makeText(getApplicationContext(), R.string.loginUnSuccesfully,
                    Toast.LENGTH_SHORT).show();


    }


}
