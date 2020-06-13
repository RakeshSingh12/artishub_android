package com.artishub.app.activities;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.databinding.ActivityLoginSignupBinding;
import com.artishub.app.fragment.LoginFragment;
import com.artishub.app.fragment.SignUpFragment;
import com.artishub.app.helpers.AlertUtil;

public class LoginSignupActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    public static boolean isShowingBackLayout;
    public static LoginSignupActivity instance;
    public ActivityLoginSignupBinding binding;
    private boolean exit = false;
    private boolean isLogin=false;

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


        binding = DataBindingUtil.setContentView(LoginSignupActivity.this, R.layout.activity_login_signup);

        instance = this;
        //**************set primary screen************//

        isLogin = getIntent().getBooleanExtra(AppConstant.IS_LOGIN,false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (savedInstanceState == null) {
            if(isLogin)
            getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).commit();
            else{
                getSupportFragmentManager().beginTransaction().add(R.id.container, new SignUpFragment()).commit();

            }
        } else {
            isShowingBackLayout = (getFragmentManager().getBackStackEntryCount() > 0);
        }
        getFragmentManager().addOnBackStackChangedListener(this::onBackStackChanged);
    }

    @Override
    public void onBackStackChanged() {
        isShowingBackLayout = (getFragmentManager().getBackStackEntryCount() > 0);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }}

}
