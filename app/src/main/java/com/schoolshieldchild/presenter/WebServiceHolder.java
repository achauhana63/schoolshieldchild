package com.schoolshieldchild.presenter;


import com.schoolshieldchild.model.login.Login;
import com.schoolshieldchild.model.uploadapps.UploadApps;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WebServiceHolder {
    @FormUrlEncoded
    @POST("StudentLogin/")
    public Call<Login> childLogin(@Field("password") String password, @Field("device_token") String deviceToken, @Field("device_type") String device_type);

    @FormUrlEncoded
    @POST("StudentApplications")
    public Call<UploadApps> uploadApps(@Field("app_package") String app_package, @Field("student_id") String student_id, @Field("app_name") String app_name, @Field("app_icon") String app_icon);




}
