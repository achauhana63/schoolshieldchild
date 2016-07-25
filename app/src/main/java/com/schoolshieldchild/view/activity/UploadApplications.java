package com.schoolshieldchild.view.activity;

import android.app.Activity;
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
import android.widget.Toast;

import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.controller.helper.prefs.SharedPref;
import com.schoolshieldchild.model.uploadimages.UploadGalleryImages;
import com.schoolshieldchild.presenter.WebServiceResult;
import com.schoolshieldchild.view.database.DataBaseHandler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadApplications extends Service {
    static Activity activity;
    static List<ResolveInfo> InstalledApps = new ArrayList<>();
    public static ArrayList<String> AllImages = new ArrayList<>();
    public static int app_value = 0;
    public static int galleryImage_value = 0;
    private static PackageManager pm;
    private Handler mHandler;
    static List<String> AllAppData = new ArrayList<>();
    static List<String> AllImagesData = new ArrayList<>();

    public UploadApplications() {
    }


    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            GetInstalledApplications();
            Toast.makeText(UploadApplications.this, "Service Started", Toast.LENGTH_LONG).show();
            mHandler.postDelayed(mRunnable, 1000);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        useHandler();
        return START_STICKY;
    }

    public void GetInstalledApplications() {
        pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        InstalledApps = pm.queryIntentActivities(intent, PackageManager.GET_META_DATA);
        UploadInstalledApplications();
        UploadGalleryImages();
    }

    public static void UploadInstalledApplications() {
        String imageIcon = DrawableToBase64(InstalledApps.get(app_value).activityInfo.loadIcon(pm));
        DataBaseHandler dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());
        AllAppData.clear();
        AllAppData.addAll(dataBaseHandler.getAllRowData());

        if (InstalledApps.size() != app_value + 1) {
            if (!AllAppData.contains(InstalledApps.get(app_value).activityInfo.packageName)) {
                WebServiceResult.UploadApps(InstalledApps.get(app_value).activityInfo.packageName, SharedPref.getString(MyApplication.STUDENT_ID), InstalledApps.get(app_value).activityInfo.loadLabel(pm).toString(), imageIcon, app_value);
            }
        }


    }

    public static void UploadGalleryImages() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());
        AllImagesData.clear();
        AllImagesData.addAll(dataBaseHandler.getAllGalleryRowData());
        HomeActivity homeActivity = new HomeActivity();
        AllImages.clear();
        AllImages.addAll(homeActivity.getAllImages());

        if (AllImages.size() != galleryImage_value + 1) {
            if (!AllImagesData.contains(AllImages.get(galleryImage_value))) {
                new UploadGalleryImages().execute(AllImages.get(galleryImage_value));

            }
        }

    }

    public static String DrawableToBase64(Drawable icon) {
        String appIcon64 = new String();
        Bitmap bitmap = drawableToBitmap(icon);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        appIcon64 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        System.out.println("appIcon64---------" + appIcon64);
        return appIcon64;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("YouWillNeverKillMe"));
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


}

