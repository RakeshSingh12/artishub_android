package com.artishub.app.application;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import com.artishub.app.R;
import com.artishub.app.activities.ForgotActivity;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.AppConfigurationModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

/**
 * Created by vivek on 28/10/17.
 */

public class MyApplication extends Application {
    RestClient.ApiRequest apiRequest;
    AppConfigurationModel appConfigurationModel;

    static MyApplication instance;
    public static String accessToken;

    public void onCreate() {

        super.onCreate();
        instance=new MyApplication();
        //callForgotPasswordApi();


    }

    public static MyApplication getInstance() {
        if (instance != null)
            return instance;
        else
            return instance = new MyApplication();
    }

    private Activity mCurrentActivity = null;
    private  boolean activityVisible;

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    public boolean isVisible() {
        return activityVisible;
    }

    public void setVisible(Boolean activityVisible) {
        this.activityVisible = activityVisible;
    }

    //****************Forgot password Api***************//
    private void callForgotPasswordApi() {

        HashMap<String, String> params = new HashMap<>();
        params.put("method","app_configuration");



        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("forgot password")
                .setResponseListener((tag, response) -> {
                    if (response != null) {
                        appConfigurationModel=new Gson().fromJson(response,AppConfigurationModel.class);
                        if(appConfigurationModel.getError_code()==0){
                            SharedPrefrencesManager.getInstance(instance).setString(AppConstant.APP_CONFIGURATION,response);
                        }

                    } else{

                    }
//                        AlertUtil.showSnackBarLong(ForgotActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                callForgotPasswordApi();
//                            }
//                        });

                })
                .setErrorListener((tag, errorMsg) -> {
//                    AlertUtil.hideProgressDialog();
//                    AlertUtil.showSnackBarLong(instance, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            callForgotPasswordApi();
//                        }
//                    });
               })
                .execute();

    }

}