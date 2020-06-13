package com.artishub.app.activities;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.SharedPrefrencesManager;

public class SplashActivity extends AppCompatActivity {
private boolean isLogin=false;
private String tutStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To change colour of status bar and navigation bar

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        isLogin=SharedPrefrencesManager.getInstance(SplashActivity.this).getBoolean(AppConstant.IS_LOGIN,false);

        tutStatus = SharedPrefrencesManager.getInstance(this).getString(AppConstant.TUTORIAL_VIEW_STATUS, "");


        new Handler().postDelayed(() -> {
            if (tutStatus.equals("true")) {
                //*****************if on one login****************//
                AppUtilis.goToActivity(SplashActivity.this, HomeActivity.class);
                finish();
            } else {
                AppUtilis.goToActivity(SplashActivity.this, TutorialActivity.class);
                finish();
            }


        }, AppConstant.SPLASH_TIME_OUT);
    }
}
