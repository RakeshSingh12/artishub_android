package com.artishub.app.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 12/16/2015.
 */
public class SharedPrefrencesManager {

    String mypreference = "my";
    private static SharedPrefrencesManager instance;

    private static SharedPreferences sharedPreferences;


    public static SharedPrefrencesManager getInstance(final Context context) {

        if (instance == null) {
            instance = new SharedPrefrencesManager();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


        }
        return instance;

    }


    public void setString(final String key, final String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(final String key, final String dval) {
        return sharedPreferences.getString(key, dval);
    }

    public void setBoolean(final String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(final String key, boolean dval) {
        return sharedPreferences.getBoolean(key, dval);
    }

    public void saveInt(final String key, final int value) {

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public int getInt(final String key, final int defVal) {

        return sharedPreferences.getInt(key, defVal);
    }

    public void removeData(final String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
        editor.commit();

    }



}



