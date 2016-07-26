package com.schoolshieldchild.view.services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;

import com.schoolshieldchild.app.MyApplication;
import com.schoolshieldchild.model.uploadimages.UploadGalleryImagesToServer;
import com.schoolshieldchild.view.database.DataBaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UploadGalleryImages extends Service {
    private static UploadGalleryImages instance;
    Handler mHandler;
    private int RESTART_UPLOAD_DURATION = 60 * 1000;
    ArrayList<String> galleryImages = new ArrayList<>();
    ArrayList<String> uploadedImages = new ArrayList<>();

    int currentIndex = 0;
    private ArrayList<String> imageNamesOnly = new ArrayList<>();

    public static UploadGalleryImages getinstance() {
        return instance;
    }

    public void useHandler() {
        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 1000);
    }

    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            galleryImages.clear();
            galleryImages.addAll(getGalleryImages());
            startGalleryImagesUpload();
            mHandler.postDelayed(this, RESTART_UPLOAD_DURATION);
        }
    };

    public ArrayList<String> getGalleryImages() {
        try {

            ArrayList<String> arrPath = new ArrayList<>();
            int ids[];
            int count;

            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor imagecursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                    null, orderBy); // UPDATE HERE

            int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
            count = imagecursor.getCount();

            ids = new int[count];
            for (int i = 0; i < count; i++) {
                imagecursor.moveToPosition(i);
                ids[i] = imagecursor.getInt(image_column_index);
                int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
                imageNamesOnly.add(imagecursor.getString(dataColumnIndex).substring(
                        imagecursor.getString(dataColumnIndex).lastIndexOf("/") + 1,
                        imagecursor.getString(dataColumnIndex).length()));
                arrPath.add(imagecursor.getString(dataColumnIndex));
            }
            return arrPath;
        } catch (NullPointerException e) {
            ArrayList<String> arrPath = new ArrayList<>();
            return arrPath;

        } catch (IndexOutOfBoundsException e) {

            ArrayList<String> arrPath = new ArrayList<>();
            return arrPath;

        } catch (Exception e) {

            ArrayList<String> arrPath = new ArrayList<>();
            return arrPath;

        }

    }

    public void startGalleryImagesUpload() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler(getApplicationContext());
        uploadedImages.clear();
        uploadedImages.addAll(dataBaseHandler.getAllGalleryImages());
        uploadGalleryImages(currentIndex);

    }

    private void uploadGalleryImages(int position) {
        if (uploadedImages.size() != galleryImages.size()) {
            //  uploadGalleryImages(currentIndex);
            if (!isImageExistInUploadedImages(galleryImages.get(position))) {
                new UploadGalleryImagesToServer(galleryImages.get(position)).execute();
            } else {
                currentIndex++;
                uploadGalleryImages(currentIndex);
            }
        }
    }

    private boolean isImageExistInUploadedImages(String imagePath) {
        boolean isExist = false;
        for (int i = 0; i < uploadedImages.size(); i++) {
            if (imagePath.equalsIgnoreCase(uploadedImages.get(i))) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public void updateDataBase(JSONObject response) {
        try {
            if (response.getString("status").equalsIgnoreCase("1")) {
                DataBaseHandler dataBaseHandler = new DataBaseHandler(MyApplication.getInstance().getApplicationContext());
                dataBaseHandler.addGalleryImagesRow(galleryImages.get(currentIndex));
                currentIndex++;
                uploadGalleryImages(currentIndex);
                System.out.println("Image Uploaded");
            } else {
                System.out.println("Image Fail to Upload");
                uploadGalleryImages(currentIndex);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        useHandler();
        return START_STICKY;
    }
}
