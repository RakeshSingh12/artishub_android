package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityRateUsBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class RateUsActivity extends AppCompatActivity {
ActivityRateUsBinding binding;
private CurrentUser currentUser;
private boolean isLogin;
private RestClient.ApiRequest apiRequest;
String rating="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding= DataBindingUtil.setContentView(RateUsActivity.this,R.layout.activity_rate_us);
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        isLogin = SharedPrefrencesManager.getInstance(this).getBoolean(AppConstant.IS_LOGIN, false);

        binding.imgLeftSide.setOnClickListener(view -> finish());

        binding.btnAddAddress.setOnClickListener(view -> {
            rating= String.valueOf(binding.ratingBar.getRating());
            if(isLogin){
                if(!rating.equals("0.0")){
                    callForgotPasswordApi();
                }else {
                    AlertUtil.showToast(this,"Please Rate Our App");
                }
            }else{
                AlertUtil.showToast(this,"Please Login before Rating");

            }
        });


    }

    private void callForgotPasswordApi() {
        AppUtilis.hideSoftKeyboard(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("method", "rating");
        params.put("user_id", currentUser.getResult().getUser_id());
        params.put("rate_value",rating);


        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("rating")
                .setResponseListener((tag, response) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {
                            String message;
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("error_code") == 0) {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(this, message);
                                finish();

                            } else {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(this, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        AlertUtil.showSnackBarLong(this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callForgotPasswordApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
//                    AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callForgotPasswordApi();
                        }
                    });
                })
                .execute();

    }
}
