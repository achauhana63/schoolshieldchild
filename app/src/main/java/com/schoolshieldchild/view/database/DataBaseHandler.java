package com.schoolshieldchild.view.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.schoolshieldchild.model.applicationprp.ApplicationPrp;

import java.util.ArrayList;

/**
 * Created by root on 20/7/16.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "STUDENT_APPLICATIONS";
    private static final String TABLE_APPS = "INSTALLED_APPS";
    private static final String KEY_ID = "id";
    private static final String KEY_PACKAGE_NAME = "package_name";
    private static final String KEY_APP_NAME = "app_name";
    private static final String TABLE_IMAGES = "GALLERY_IMAGES";
    private static final String KEY_IMAGEPATH = "IMAGE_PATH";

    private static final String CREATE_TABLE_APPS = "CREATE TABLE "
            + TABLE_APPS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PACKAGE_NAME
            + " TEXT," + KEY_APP_NAME
            + " TEXT" + ")";


    private static final String CREATE_TABLE_GALLERY_IMAGES = "CREATE TABLE "
            + TABLE_IMAGES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMAGEPATH
            + " TEXT" + ")";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_APPS);
        sqLiteDatabase.execSQL(CREATE_TABLE_GALLERY_IMAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);

    }

    public boolean addRow(String appname, String packagename) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_APP_NAME, appname);
            contentValues.put(KEY_PACKAGE_NAME, packagename);
            long apps = db.insert(TABLE_APPS, null, contentValues);
            return true;
        } catch (SQLiteException e) {
            return false;

        } catch (Exception e) {
            return false;

        }
    }

    public void addGalleryImagesRow(String imagepath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_IMAGEPATH, imagepath);
        long images = db.insert(TABLE_IMAGES, null, contentValues);
    }

    public ArrayList<ApplicationPrp> getAllRowData() {
        ArrayList<ApplicationPrp> array_list = new ArrayList<ApplicationPrp>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_APPS, null);
        while (res.moveToNext()) {
            ApplicationPrp applicationPrp = new ApplicationPrp();
            applicationPrp.setApplictionname(res.getString(res.getColumnIndex(KEY_APP_NAME)));
            applicationPrp.setPackageName(res.getString(res.getColumnIndex(KEY_PACKAGE_NAME)));
            array_list.add(applicationPrp);
        }
        return array_list;
    }

    public ArrayList<String> getAllGalleryRowData() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_IMAGES, null);
        while (res.moveToNext()) {
            array_list.add(res.getString(res.getColumnIndex(KEY_IMAGEPATH)));
        }
        return array_list;
    }

    public boolean deleteApplication(String packageName) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String qry = "delete from " + TABLE_APPS + " where " + KEY_PACKAGE_NAME + "='" + packageName + "'";
            db.execSQL(qry);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
