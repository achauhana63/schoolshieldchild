package com.schoolshieldchild.controller.helper.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.schoolshieldchild.app.MyApplication;


/**
 * Created by root on 26/8/15.
 */
public class SharedPref {

    private static String pref_name = "CHILD";
    private static SharedPreferences pref;


    public static void setString(String var_name, String var_value) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        Editor edit = pref.edit();
        edit.putString(var_name, var_value);
        edit.apply();

    }

    public static String getString(String var_name) {
        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
                Context.MODE_PRIVATE);
        return pref.getString(var_name, "");
    }

//    public static void setInt(String var_name, int var_value) {
//        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
//                Context.MODE_PRIVATE);
//        Editor edit = pref.edit();
//        edit.putInt(var_name, var_value);
//        edit.commit();
//    }
//
//    public static int getInt(String var_name) {
//        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
//                Context.MODE_PRIVATE);
//        return pref.getInt(var_name, 0);
//    }
//
//
//    public static void setBoolean(String var_name, boolean var_value) {
//        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
//                Context.MODE_PRIVATE);
//        Editor edit = pref.edit();
//        edit.putBoolean(var_name, var_value);
//        edit.commit();
//    }
//
//    public static boolean getBoolean(String var_name) {
//        SharedPreferences pref = MyApplication.getInstance().getSharedPreferences(pref_name,
//                Context.MODE_PRIVATE);
//        return pref.getBoolean(var_name, false);
//    }
//

    public static void ClearData() {
        pref = MyApplication.getInstance().getSharedPreferences(pref_name, Context.MODE_PRIVATE);
        Editor edit = pref.edit();
        edit.clear();
        edit.commit();
    }
}
