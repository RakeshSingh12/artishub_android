package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.MsgConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityForgotBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.AppValidator;
import com.artishub.app.helpers.RestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class ForgotActivity extends AppCompatActivity {
private ActivityForgotBinding binding;
private RestClient.ApiRequest apiRequest;
private String emailPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
       binding= DataBindingUtil.setContentView(ForgotActivity.this,R.layout.activity_forgot);
       //setGradientText();
        AppUtilis.setGradientTextColor(ForgotActivity.this,binding.txtForgotPasswordTitle);

       //***************Click listener on Submit button*************//
        binding.btnSubmit.setOnClickListener(view -> {
            if(validData()){
                emailPhone=binding.edtEmail.getText().toString();
                callForgotPasswordApi();
            }
        });


    }




    //********************Validation of Data*********************//
    private boolean validData(){
        if(!AppValidator.isValidEmailPhone(ForgotActivity.this,binding.edtEmail, MsgConstant.EMAIL_PHONE_ERROR_VOID)){
            return false;
        }

        return  true;
    }

    //****************Forgot password Api***************//
    private void callForgotPasswordApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("username",emailPhone);
        params.put("method","forgotpassword");



        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("forgot password")
                .setResponseListener((tag, response) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        try {
                            String message;
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("error_code") == 0) {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(ForgotActivity.this, message);
                                finish();

                            } else {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(ForgotActivity.this, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        AlertUtil.showSnackBarLong(ForgotActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callForgotPasswordApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
//                    AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(ForgotActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callForgotPasswordApi();
                        }
                    });
                })
                .execute();

    }
}
