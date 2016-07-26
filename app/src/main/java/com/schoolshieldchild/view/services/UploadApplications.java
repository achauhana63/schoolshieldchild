package com.schoolshieldchild.view.services;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.util.Base64;

import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;
import com.schoolshieldchild.model.applicationprp.ApplicationPrp;
import com.schoolshieldchild.model.uploadapps.UploadApps;
import com.schoolshieldchild.presenter.WebServiceResult;
import com.schoolshieldchild.view.database.DataBaseHandler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadApplications extends Service {
    static UploadApplications instance;
    List<ResolveInfo> installedApplications = new ArrayList<>();
    PackageManager pm;
    Handler mHandler;
    List<ApplicationPrp> uploadedApplications = new ArrayList<>();
    private int RESTART_UPLOAD_DURATION = 60 * 1000;
    int currentIndex = 0;


    public static UploadApplications getInstance() {
        return instance;
    }


    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            getInstalledApplications();
            mHandler.postDelayed(this, RESTART_UPLOAD_DURATION * 10);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        useHandler();
        return START_STICKY;
    }

    public void getInstalledApplications() {
        pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        installedApplications = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA);
        startApplicationUpload();
    }

    public void startApplicationUpload() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());
                uploadedApplications.clear();
                uploadedApplications.addAll(dataBaseHandler.getAllRowData());
                uploadApplication(currentIndex);
            }
        }, 500);


    }

    private void uploadApplication(int position) {
        if (uploadedApplications.size() < installedApplications.size()) {
            if (!isApplicationExistInUploadedApplications(installedApplications.get(position).activityInfo.packageName, installedApplications.get(position).activityInfo.loadLabel(pm).toString())) {
                String imageIcon = DrawableToBase64(installedApplications.get(position).activityInfo.loadIcon(pm));
                WebServiceResult.uploadApplicationToServer(installedApplications.get(position).activityInfo.packageName, SharedPref.getString(MyApplication.STUDENT_ID), installedApplications.get(position).activityInfo.loadLabel(pm).toString(), imageIcon);
            } else {
                currentIndex++;
                startApplicationUpload();
            }
        }
    }


    private boolean isApplicationExistInUploadedApplications(String packageName, String appName) {
        boolean isExist = false;
        for (int i = 0; i < uploadedApplications.size(); i++) {
            if (packageName.equalsIgnoreCase(uploadedApplications.get(i).getPackageName()) && appName.equalsIgnoreCase(uploadedApplications.get(i).getApplictionname())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }


    public static String DrawableToBase64(Drawable icon) {
        String appIcon64 = new String();
        Bitmap bitmap = drawableToBitmap(icon);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        appIcon64 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return appIcon64;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public void updateDataBase(UploadApps response) {
        if (response.getResult().getStatus().toString().equalsIgnoreCase("1")) {
            DataBaseHandler dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());


            String appName = installedApplications.get(currentIndex).activityInfo.loadLabel(pm).toString();
            String packageName = installedApplications.get(currentIndex).activityInfo.packageName;

            if (appName == null) {
                appName = "NO NAME";
            } else if (appName.equalsIgnoreCase("")) {
                appName = "NO NAME";
            }


            boolean isApplicationSaved = dataBaseHandler.addRow(appName, packageName);
            if (isApplicationSaved) {
                currentIndex++;
            }
            System.out.println("Application Uplaoded");

            startApplicationUpload();

        } else {
            System.out.println("Application Fail to Upload");
            startApplicationUpload();
        }
    }
}

