package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.MsgConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityCompleteProfileBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.AppValidator;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.AppConfigurationModel;
import com.artishub.app.model.CurrentUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class CompleteProfileActivity extends AppCompatActivity {
private ActivityCompleteProfileBinding binding;
    String[] listDeliveryPrefrences = {"Collect","Wholesaler Delivery","Logistics"};
    String[] listPaymentMode = {"Mobile Money","E-Wallet","Bank Transfer","Card"};
    ArrayList<String> deliveryModeList=new ArrayList<>();
    ArrayList<String> paymentModeList=new ArrayList<>();
    private String name,address,paymentMode,deliveryPrefrences,houseNo;
    private RestClient.ApiRequest apiRequest;
    private CurrentUser currentUser;
    private AppConfigurationModel appConfigurationModel;
    private boolean exit =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding= DataBindingUtil.setContentView(CompleteProfileActivity.this,R.layout.activity_complete_profile);

        //*********get current user details**********//
        currentUser=new Gson().fromJson(SharedPrefrencesManager.getInstance(CompleteProfileActivity.this).getString(AppConstant.KEY_CURRENT_USER,""),CurrentUser.class);
        appConfigurationModel=new Gson().fromJson(SharedPrefrencesManager.getInstance(CompleteProfileActivity.this).getString(AppConstant.APP_CONFIGURATION,""),AppConfigurationModel.class);

                for(int i=0;i<appConfigurationModel.getDelivery_mode().size();i++){
                 deliveryModeList.add(appConfigurationModel.getDelivery_mode().get(i).getDelivery_mode_name());
                }


        for(int i=0;i<appConfigurationModel.getPayment_methods().size();i++){
            paymentModeList.add(appConfigurationModel.getPayment_methods().get(i).getPayment_method_name());
        }

        //setGradientText();
        AppUtilis.setGradientTextColor(CompleteProfileActivity.this,binding.txtCompleteProfileTitle);

        //***********click listener on Delivery Prefrences************//
        binding.txtDeliveryPrefrences.setOnClickListener(view -> {
            setDeliveryPrefrencesList();
        });

        //***********click listener on Payment Mode************//
        binding.txtPaymentMode.setOnClickListener(view -> {
            setPaymentModeList();
        });

        //********Submit button click********//
        binding.btnSubmit.setOnClickListener(view -> {
            if(checkData()){
                name=binding.edtName.getText().toString();
                address=binding.edtAddress.getText().toString();
                houseNo=binding.edtAddressOne.getText().toString();
                callProfileApi();
            }
        });

    }


    //**********Call complete profile Api******************//
    private void callProfileApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("name",name);
        params.put("method","complete_profile");
        params.put("delivery_preference",deliveryPrefrences);
        params.put("payment_mode",paymentMode);
        params.put("address",address);
        params.put("house_no",houseNo);
        params.put("country_code",currentUser.getResult().getCountry_code());
        params.put("mobile_number",currentUser.getResult().getMobile_number());
        params.put("user_id",currentUser.getResult().getUser_id());



        apiRequest = new RestClient.ApiRequest(LoginSignupActivity.instance);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("complete profile")
                .setResponseListener((tag, response) -> {
                   // AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        currentUser=new Gson().fromJson(response,CurrentUser.class);
                        if(currentUser.getError_code()==0){
                            SharedPrefrencesManager.getInstance(CompleteProfileActivity.this).setString(AppConstant.KEY_CURRENT_USER,response);
                            SharedPrefrencesManager.getInstance(CompleteProfileActivity.this).setBoolean(AppConstant.IS_LOGIN,true);
                            SharedPrefrencesManager.getInstance(CompleteProfileActivity.this).setString(AppConstant.KEY_LOGIN_USER_ID,currentUser.getResult().getUser_id());
                            AppUtilis.goToActivity(CompleteProfileActivity.this, HomeActivity.class);
                            finish();
                        }else if(currentUser.getError_code()==1){
                            AlertUtil.showToast(CompleteProfileActivity.this,currentUser.getError_string());
                        }


                    } else
                        AlertUtil.showSnackBarLong(CompleteProfileActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callProfileApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(CompleteProfileActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callProfileApi();
                        }
                    });
                })
                .execute();
    }

    //********Method for picker genderList*****//
    private void setDeliveryPrefrencesList() {

        ArrayAdapter spinnerDelivery = new ArrayAdapter(CompleteProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, deliveryModeList);
        final ListPopupWindow popupWindow = new ListPopupWindow(CompleteProfileActivity.this);
        popupWindow.setOnItemClickListener((parent, view, position, id) -> {
            binding.txtDeliveryPrefrences.setText(listDeliveryPrefrences[position]);
            deliveryPrefrences=deliveryModeList.get(position);

            if (position != 0) {

            } else {

            }
            popupWindow.dismiss();
        });
        popupWindow.setContentWidth(android.support.v7.widget.ListPopupWindow.WRAP_CONTENT);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setAnchorView(binding.txtDeliveryPrefrences);
        popupWindow.setAdapter(spinnerDelivery);
        popupWindow.show();

    }

    //********Method for picker genderList*****//
    private void setPaymentModeList() {
        ArrayAdapter spinnerGender = new ArrayAdapter(CompleteProfileActivity.this, android.R.layout.simple_spinner_dropdown_item, paymentModeList);
        final ListPopupWindow popupWindow = new ListPopupWindow(CompleteProfileActivity.this);
            popupWindow.setOnItemClickListener((parent, view, position, id) -> {
            binding.txtPaymentMode.setText(listPaymentMode[position]);
          paymentMode=paymentModeList.get(position);
            if (position != 0) {

            } else {

            }
            popupWindow.dismiss();
        });
        popupWindow.setContentWidth(android.support.v7.widget.ListPopupWindow.WRAP_CONTENT);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setAnchorView(binding.txtPaymentMode);
        popupWindow.setAdapter(spinnerGender);
        popupWindow.show();

    }

    //**********check the data***************//
    private boolean checkData() {
        if (!AppValidator.isValidName(LoginSignupActivity.instance, binding.edtName, MsgConstant.FUll_NAME_ERROR_VOID)) {
            return false;
        }else if(!validData(deliveryPrefrences,MsgConstant.DELIVERY_OPTION)){
            return false;
        }
        else if(!validData(binding.edtAddress.getText().toString(),MsgConstant.ADDRESS_ERROR_VOID))
        {
            binding.edtAddress.requestFocus();
            return false;

        }
        else if(!validData(binding.edtAddressOne.getText().toString(),MsgConstant.HOUSE_NO_ERROR_VOID))
        {
            binding.edtAddressOne.requestFocus();
            return false;

        }
        else if(!validData(paymentMode,MsgConstant.PAYMENT_OPTION)){
            return false;
        }


        return true;
    }


    private boolean validData(String s,String msg){
        if(s!=null&&!s.equals("")){
            return true;
        }
        else{
            AlertUtil.showToast(CompleteProfileActivity.this, msg);
            return false;

        }


    }

    @Override
    public void onBackPressed() {
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
