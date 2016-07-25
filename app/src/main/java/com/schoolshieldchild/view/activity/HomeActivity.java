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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    static List<ResolveInfo> InstalledApps = new ArrayList<>();
    public static int app_value = 0;
    private static PackageManager pm;
    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        activity = HomeActivity.this;
        setContentView(R.layout.activity_home);

        Intent pushIntent = new Intent(this, UploadApplications.class);
        startService(pushIntent);

    }

    public class StartMyServiceAtBootReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "BOOT_COMPLETED", Toast.LENGTH_LONG).show();
            if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                Intent serviceIntent = new Intent(context, UploadApplications.class);
                startService(serviceIntent);
            }
        }
    }

    public ArrayList<String> getAllImages() {
        ArrayList<String> AllImages = new ArrayList<>();
        Cursor cur = null;
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN
        };
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        try {
            cur = activity.getContentResolver().query(images, null, null, null, null);
        } catch (Exception e) {
        }
        Log.i("ListingImages", " query count=" + cur.getCount());
        if (cur.moveToFirst()) {
            String bucket;

            int bucketColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATA);

            do {
                bucket = cur.getString(bucketColumn);
                Log.i("ListingImages", " bucket=" + bucket);
                AllImages.add(bucket);

            } while (cur.moveToNext());

        }

        return AllImages;
    }

}
