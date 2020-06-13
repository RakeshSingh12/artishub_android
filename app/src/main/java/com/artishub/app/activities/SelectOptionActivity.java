package com.artishub.app.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.databinding.ActivitySelectOptionBinding;
import com.artishub.app.helpers.AppUtilis;

public class SelectOptionActivity extends AppCompatActivity {
    private ActivitySelectOptionBinding binding;
    private boolean exit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //*********To change colour of status bar and navigation bar**********//
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(SelectOptionActivity.this, R.layout.activity_select_option);


        //*************click on Login button**************//
        binding.btnLogin.setOnClickListener(view -> {
            Intent i=new Intent(SelectOptionActivity.this,LoginSignupActivity.class);
            i.putExtra(AppConstant.IS_LOGIN,true);
            startActivity(i);
            finish();

        });

        //**********clicl on signup button***************//
        binding.btnSignUp.setOnClickListener(view -> {
            Intent i=new Intent(SelectOptionActivity.this,LoginSignupActivity.class);
            i.putExtra(AppConstant.IS_LOGIN,false);
            startActivity(i);
            finish();

        });



    }

//    @Override
//    public void onBackPressed() {
//
//        if (exit) {
//            finish(); // finish activity
//        } else {
//            Toast.makeText(this, "Press Back again to Exit.",
//                    Toast.LENGTH_SHORT).show();
//            exit = true;
//            new Handler().postDelayed(() -> exit = false, 2 * 1000);
//
//        }
//    }
}
