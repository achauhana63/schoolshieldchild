package com.schoolshieldchild.presenter;

import android.content.Intent;
import android.widget.Toast;

import com.schoolshieldchild.R;
import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;
import com.schoolshieldchild.controller.utils.DialogManager;
import com.schoolshieldchild.model.login.Login;
import com.schoolshieldchild.model.uploadapps.UploadApps;
import com.schoolshieldchild.view.activity.HomeActivity;
import com.schoolshieldchild.view.activity.LoginActivity;
import com.schoolshieldchild.view.activity.UploadApplications;
import com.schoolshieldchild.view.database.DataBaseHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServiceResult {
    static List<String> AllAppData = new ArrayList<>();


    public static void ChildLogin(String password, String deviceToken, String deviceType) {
        Call<Login> responseCall = WebServiceConnection.getInstance().childLogin(password, deviceToken, deviceType);
        responseCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                DialogManager.stopProgressDialog();
                if (response.body().getResult().getStatus().toString().equalsIgnoreCase("1")) {
                    SharedPref.setString(MyApplication.STUDENT_ID, response.body().getResult().getStudentId().toString());
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), R.string.loginsuccessfully, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MyApplication.getInstance().getApplicationContext(), HomeActivity.class);
                    LoginActivity.activity.startActivity(intent);
                    LoginActivity.activity.finish();
                } else {
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });

    }

    public static void UploadApps(final String app_package, String student_id, final String app_name, String app_icon, final int appval) {

        Call<UploadApps> responseCall = WebServiceConnection.getInstance().uploadApps(app_package, student_id, app_name, app_icon);
        responseCall.enqueue(new Callback<UploadApps>() {
            @Override
            public void onResponse(Call<UploadApps> call, Response<UploadApps> response) {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());
                if (response.body().getResult().getStatus().toString().equalsIgnoreCase("1")) {
                    UploadApplications.app_value = appval + 1;
                    UploadApplications.UploadInstalledApplications();
                    AllAppData.addAll(dataBaseHandler.getAllRowData());
                    if (!AllAppData.contains(app_package)) {
                        dataBaseHandler.addRow(app_name, app_package);
                    }
                } else {
                    UploadApplications.app_value = appval;
                    UploadApplications.UploadInstalledApplications();
                }
            }

            @Override
            public void onFailure(Call<UploadApps> call, Throwable t) {
                // DialogManager.stopProgressDialog();
            }
        });

    }

}