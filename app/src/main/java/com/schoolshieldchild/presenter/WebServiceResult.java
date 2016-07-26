package com.schoolshieldchild.presenter;

import com.schoolshieldchild.controller.utils.DialogManager;
import com.schoolshieldchild.model.login.Login;
import com.schoolshieldchild.model.uploadapps.UploadApps;
import com.schoolshieldchild.view.activity.LoginActivity;
import com.schoolshieldchild.view.services.UploadApplications;

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
                LoginActivity.getInstance().updateLogin(response.body());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                DialogManager.stopProgressDialog();
            }
        });

    }

    public static void uploadApplicationToServer(final String app_package, String student_id, final String app_name, String app_icon) {

        Call<UploadApps> responseCall = WebServiceConnection.getInstance().uploadApps(app_package, student_id, app_name, app_icon);
        responseCall.enqueue(new Callback<UploadApps>() {
            @Override
            public void onResponse(Call<UploadApps> call, Response<UploadApps> response) {
                try {
                    UploadApplications.getInstance().updateDataBase(response.body());
                }
                catch (NullPointerException e)
                {

                }
            }

            @Override
            public void onFailure(Call<UploadApps> call, Throwable t) {
                // DialogManager.stopProgressDialog();
            }
        });

    }

}