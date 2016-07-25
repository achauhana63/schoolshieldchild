package com.schoolshieldchild.model.uploadimages;

import android.os.AsyncTask;
import android.util.Log;

import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;
import com.schoolshieldchild.view.activity.UploadApplications;
import com.schoolshieldchild.view.database.DataBaseHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 25/7/16.
 */
public class UploadGalleryImages extends AsyncTask<String, String, String> {
    private File imagefile;
    private String galleryImageval = "";
    static List<String> AllImagesData = new ArrayList<>();
    private String galleryImagePath = "";


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        galleryImageval = strings[1];
        galleryImagePath = strings[0];

        MultipartEntity reqEntity = new MultipartEntity();

        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost("http://112.196.23.162:8026/schoolbully/uploadGallery");
        try {

            reqEntity.addPart("student_id",
                    new StringBody(SharedPref.getString(MyApplication.STUDENT_ID)));
            try {
                if (strings[0].equalsIgnoreCase("")) {
                    imagefile = null;
                } else {
                    imagefile = new File(strings[0]);
                }
                if (imagefile != null) {
                    reqEntity.addPart("galleryimage", new FileBody(imagefile, "image/jpg"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                reqEntity.addPart("galleryimage", new FileBody(null, "image/jpg"));

            }
            request.setEntity(reqEntity);
            HttpResponse httpResponse = client.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            response = sb.toString();
            Log.v("response", "" + response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject resultResponse = jsonObject.getJSONObject("result");
            if (resultResponse.get("status").toString().equalsIgnoreCase("1")) {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());
                UploadApplications.galleryImage_value = Integer.parseInt(galleryImageval) + 1;
                UploadApplications.UploadGalleryImages();
                AllImagesData.addAll(dataBaseHandler.getAllGalleryRowData());
                if (!AllImagesData.contains(galleryImagePath)) {
                    dataBaseHandler.addGalleryImagesRow(galleryImagePath);
                }


            } else {
                UploadApplications.galleryImage_value = Integer.parseInt(galleryImageval) + 1;
                UploadApplications.UploadGalleryImages();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
