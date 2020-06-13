package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.databinding.ActivityAboutUsBinding;
import com.artishub.app.helpers.AppUtilis;

public class AboutUsActivity extends AppCompatActivity {
ActivityAboutUsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
       binding= DataBindingUtil.setContentView(AboutUsActivity.this,R.layout.activity_about_us);

        AppUtilis.setGradientTextColor(AboutUsActivity.this,binding.txtTitleOne);
        AppUtilis.setGradientTextColor(AboutUsActivity.this,binding.txtTitleTwo);
        AppUtilis.setGradientTextColor(AboutUsActivity.this,binding.txtTitleThree);
        binding.imgLeftSide.setOnClickListener(view -> finish());
    }
}
