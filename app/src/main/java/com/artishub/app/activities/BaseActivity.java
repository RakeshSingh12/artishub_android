package com.artishub.app.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.artishub.app.application.MyApplication;


/**
 * Created by vivek on 15/6/17.
 */

public class BaseActivity extends FragmentActivity {
    protected MyApplication mMyApp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMyApp = MyApplication.getInstance();
        mMyApp.setVisible(true);
    }

    protected void onResume() {
        super.onResume();
        mMyApp.setVisible(true);
        mMyApp.setCurrentActivity(this);
    }

    protected void onPause() {
        clearReferences();
        super.onPause();

    }

    protected void onDestroy() {
        clearReferences();
        super.onDestroy();

    }

    private void clearReferences() {
        Activity currActivity = mMyApp.getCurrentActivity();
        if (this.equals(currActivity)) {
            // mMyApp.setCurrentActivity(null);
            mMyApp.setVisible(false);
        }

    }
}
