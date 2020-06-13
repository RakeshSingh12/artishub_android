package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.databinding.ActivityHelpCenterBinding;

public class HelpCenterActivity extends AppCompatActivity {
    private ActivityHelpCenterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding= DataBindingUtil.setContentView(HelpCenterActivity.this,R.layout.activity_help_center);
        binding.imgLeftSide.setOnClickListener(view -> finish());

    }
}
