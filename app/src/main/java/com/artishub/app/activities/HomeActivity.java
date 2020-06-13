package com.artishub.app.activities;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.artishub.app.R;
import com.artishub.app.adapter.HomePagerAdapter;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityHomeBinding;
import com.artishub.app.databinding.DrawerLayoutBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.artishub.app.model.MyAddressModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class HomeActivity extends AppCompatActivity {
    protected ActivityHomeBinding binding;
    public static HomeActivity instance;
    private DrawerLayoutBinding drawerLayoutBinding;
    private boolean exit = false;
    private int currentTab;
    private boolean isLogin;
    private CurrentUser currentUser;
    private RestClient.ApiRequest apiRequest;
    private RestClient.ApiRequest apiAddAddressRequest;
    private String toFragment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }

        binding = DataBindingUtil.setContentView(HomeActivity.this, R.layout.activity_home);
        drawerLayoutBinding = binding.drawerLayout;
        instance = this;
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(HomeActivity.this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        isLogin = SharedPrefrencesManager.getInstance(HomeActivity.this).getBoolean(AppConstant.IS_LOGIN, false);

        if (isLogin) {
            callUpdateCartApi();
        }
        if (getIntent().hasExtra("toFragment"))
            toFragment = getIntent().getStringExtra("toFragment");


        initTabs();

        //********set tabTitle*******//
        currentTab = binding.pager.getCurrentItem();
        setTabTitle(binding.pager.getCurrentItem());

        //******page change listener for tab title*******//
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //****************side menu icon click**************//
        binding.imgMenu.setOnClickListener(v -> {
            if (binding.activityHome.isDrawerOpen(GravityCompat.START)) {
                binding.activityHome.closeDrawer(GravityCompat.START);
            } else {
                AppUtilis.hideSoftKeyboard(HomeActivity.this);
                binding.activityHome.openDrawer(GravityCompat.START);

            }

        });

        //*************rightSide icon click*****************//
        binding.imgRightSide.setOnClickListener(view -> {

            if (currentTab == 1) {
                if (isLogin) {
                    AppUtilis.goToActivity(HomeActivity.this, EditProfileActivity.class);
                }
            } else if (currentTab == 2) {

            } else if (currentTab == 3) {

            } else {
                AlertUtil.showToast(HomeActivity.this, "UNDER DEVELOPMENT");
            }


        });

        //****************side menu item click***************//
        drawerLayoutBinding.txtContactNo.setOnClickListener(view -> {
            if (!isLogin) {
                AppUtilis.goToActivity(HomeActivity.this, SelectOptionActivity.class);
            }
        });

        drawerLayoutBinding.navSetting.setOnClickListener(view -> {
            AppUtilis.goToActivity(HomeActivity.this, SettingsActivity.class);
        });

        drawerLayoutBinding.navLogout.setOnClickListener(view -> {
            AlertUtil.showAlertDialog(HomeActivity.instance, "ArtisHub", "Are you sure you want to logout?", (dialogInterface, i) -> {
                SharedPrefrencesManager.getInstance(HomeActivity.this).removeData(AppConstant.IS_LOGIN);
                SharedPrefrencesManager.getInstance(HomeActivity.this).removeData(AppConstant.KEY_CURRENT_USER);
                SharedPrefrencesManager.getInstance(HomeActivity.this).removeData(AppConstant.KEY_LOGIN_USER_ID);
                SharedPrefrencesManager.getInstance(HomeActivity.this).removeData("ADDRESS");
                SharedPrefrencesManager.getInstance(HomeActivity.this).removeData("CartItem");
                SharedPrefrencesManager.getInstance(HomeActivity.this).removeData("loginSteps");
                toFragment="";




                finish();
                startActivity(getIntent());
            }, (dialogInterface, i) -> dialogInterface.dismiss());


        });

        drawerLayoutBinding.navAboutUs.setOnClickListener(view -> {
            AppUtilis.goToActivity(HomeActivity.this, AboutUsActivity.class);
        });
        drawerLayoutBinding.navRateUs.setOnClickListener(view -> {
            AppUtilis.goToActivity(HomeActivity.this, RateUsActivity.class);
        });
        drawerLayoutBinding.navPrivacy.setOnClickListener(view -> {
            AppUtilis.goToActivity(HomeActivity.this, PrivacyPolicyActivity.class);
        });
        drawerLayoutBinding.navHelpCenter.setOnClickListener(view -> {
            AppUtilis.goToActivity(HomeActivity.this, HelpCenterActivity.class);
        });
        drawerLayoutBinding.navHome.setOnClickListener(view -> {
            toFragment="";
            finish();
            startActivity(getIntent());
        });

        /*set data in side menu*/
        setDataSideMenu();


    }

    private void callUpdateCartApi() {
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "get_cart_count");
        params.put("user_id", currentUser.getResult().getUser_id());


        apiRequest = new RestClient.ApiRequest(HomeActivity.instance);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("get")
                .setResponseListener((tag, response) -> {
                    AlertUtil.hideProgressDialog();
                    if (response != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg;
                            if (jsonObject.getInt("error_code") == 0) {
                                msg = jsonObject.getString("error_string");
                                SharedPrefrencesManager.getInstance(HomeActivity.this).setString("CartItem", "" + jsonObject.getInt("result"));


                            } else {
                                msg = jsonObject.getString("error_string");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                    }

                })

                .setErrorListener((tag, errorMsg) -> {
                })
                .execute();

    }


    private void callAddAddressApi() {
        AppUtilis.hideSoftKeyboard(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", currentUser.getResult().getUser_id());
        params.put("method", "getAlladdress");


        apiAddAddressRequest = new RestClient.ApiRequest(this);
        apiAddAddressRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("get All address")
                .setResponseListener((tag, response) -> {
                    if (response != null) {
                        MyAddressModel myAddressModel = new Gson().fromJson(response, MyAddressModel.class);
                        if (myAddressModel.getError_code() == 0) {

                            SharedPrefrencesManager.getInstance(HomeActivity.this).setString(AppConstant.ADDRESS, response);

                        } else {
                            SharedPrefrencesManager.getInstance(HomeActivity.this).setString(AppConstant.ADDRESS, "");

                        }


                    } else {

                    }


                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();


                })
                .execute();
    }


    //********************initiat tab******************//
    private void initTabs() {
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), binding.tabLayout);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(4);
        binding.pager.setPagingEnabled(false);
        binding.tabLayout.setupWithViewPager(binding.pager);
        if (toFragment.equals("ToMyBag"))
            binding.pager.setCurrentItem(3);
        if (toFragment.equals("ToMyCart"))
            binding.pager.setCurrentItem(2);


    }

    //******************set selected tab title**************************//
    private void setTabTitle(int position) {
        switch (position) {
            case 0:
                binding.txtHeading.setText("Product");
                binding.imgRightSide.setImageResource(R.drawable.notification);
                binding.viewOne.setVisibility(View.VISIBLE);
                binding.viewTwo.setVisibility(View.INVISIBLE);
                binding.viewThree.setVisibility(View.INVISIBLE);
                binding.viewFour.setVisibility(View.INVISIBLE);
                currentTab = position;


                break;
            case 1:
                binding.txtHeading.setText("My Profile");
                if (isLogin) {
                    binding.imgRightSide.setImageResource(R.drawable.icon_edit);
                } else {
                    binding.imgRightSide.setImageResource(0);

                }
                binding.viewOne.setVisibility(View.INVISIBLE);
                binding.viewTwo.setVisibility(View.VISIBLE);
                binding.viewThree.setVisibility(View.INVISIBLE);
                binding.viewFour.setVisibility(View.INVISIBLE);
                currentTab = position;
                break;
            case 2:
                binding.txtHeading.setText("My Cart");
                binding.imgRightSide.setImageResource(R.drawable.notification);
                binding.viewOne.setVisibility(View.INVISIBLE);
                binding.viewTwo.setVisibility(View.INVISIBLE);
                binding.viewThree.setVisibility(View.VISIBLE);
                binding.viewFour.setVisibility(View.INVISIBLE);
                currentTab = position;
                break;
            case 3:
                binding.txtHeading.setText("My Orders");
                binding.imgRightSide.setImageResource(R.drawable.notification);
                binding.viewOne.setVisibility(View.INVISIBLE);
                binding.viewTwo.setVisibility(View.INVISIBLE);
                binding.viewThree.setVisibility(View.INVISIBLE);
                binding.viewFour.setVisibility(View.VISIBLE);
                currentTab = position;
                break;


        }
    }

    //******************set data****************//
    public void setDataSideMenu() {
        if (isLogin) {
            currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(HomeActivity.this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
            drawerLayoutBinding.txtContactNo.setText(currentUser.getResult().getName());
            drawerLayoutBinding.txtContactNo.setTextSize(18);
            drawerLayoutBinding.txtExistingUserName.setVisibility(View.INVISIBLE);
            drawerLayoutBinding.navLogout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.activityHome.isDrawerOpen(GravityCompat.START)) {
            binding.activityHome.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(() -> exit = false, 2 * 1000);

            }

        }
    }


    @Override
    protected void onResume() {
        if (isLogin)
            callAddAddressApi();
        super.onResume();
    }
}
