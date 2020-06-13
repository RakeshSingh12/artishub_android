package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.databinding.ActivitySettingsBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity {
ActivitySettingsBinding binding;
private CurrentUser currentUser;
private boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }

        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        isLogin = SharedPrefrencesManager.getInstance(this).getBoolean(AppConstant.IS_LOGIN, false);

        binding= DataBindingUtil.setContentView(SettingsActivity.this,R.layout.activity_settings);
        binding.imgLeftSide.setOnClickListener(view -> finish());

        binding.rlChangePassword.setOnClickListener(view -> {
            if(isLogin){
            AppUtilis.goToActivity(this,ChangePasswordActivity.class);}
            else{
            }
        });
    }
}
