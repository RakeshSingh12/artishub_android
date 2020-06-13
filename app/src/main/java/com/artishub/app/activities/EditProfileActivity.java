package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.MsgConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityEditProfileBinding;
import com.artishub.app.databinding.FragmentProfileBinding;
import com.artishub.app.fragment.ProfileFragment;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.AppValidator;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.google.gson.Gson;

import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class EditProfileActivity extends AppCompatActivity {
    private CurrentUser currentUser;
    private ActivityEditProfileBinding binding;
    private RestClient.ApiRequest apiRequest;
    private String name, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(EditProfileActivity.this, R.layout.activity_edit_profile);
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(EditProfileActivity.this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);

        /*set data*/
        binding.edtName.setText(currentUser.getResult().getName());
        binding.edtName.setSelection(binding.edtName.getText().length());
        binding.edtEmail.setText(currentUser.getResult().getEmail());
        binding.edtEmail.setSelection(binding.edtEmail.getText().length());

        binding.edtPhone.setText(currentUser.getResult().getMobile_number());
        binding.edtPhone.setSelection(binding.edtPhone.getText().length());


        /*back icon listener*/
        binding.imgBack.setOnClickListener(view -> {
            finish();
        });

        binding.btnSubmit.setOnClickListener(view -> {
            if (checkData()) {
                name = binding.edtName.getText().toString();
                phone = binding.edtPhone.getText().toString();
                email = binding.edtEmail.getText().toString();
                callProfileApi();
            }
        });
    }

    //**********check the data***************//
    private boolean checkData() {
        if (!AppValidator.isValidName(EditProfileActivity.this, binding.edtName, MsgConstant.FUll_NAME_ERROR_VOID)) {
            return false;
        } else if (!AppValidator.isValidMobile(EditProfileActivity.this, binding.edtPhone, MsgConstant.PHONE_ERROR_VOID)) {
            return false;
        } else if (!AppValidator.isValidEmail(LoginSignupActivity.instance, binding.edtEmail, MsgConstant.EMAIL_ERROR_VOID)) {
            return false;
        }

        return true;
    }

    //**********Call complete profile Api******************//
    private void callProfileApi() {
        currentUser = new Gson().fromJson(SharedPrefrencesManager.getInstance(EditProfileActivity.this).getString(AppConstant.KEY_CURRENT_USER, ""), CurrentUser.class);
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("mobile_number", phone);
        params.put("method", "update_profile");
        params.put("user_id", currentUser.getResult().getUser_id());


        apiRequest = new RestClient.ApiRequest(EditProfileActivity.this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("Signup")
                .setResponseListener((tag, response) -> {
                    // AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    if (response != null) {
                        currentUser = new Gson().fromJson(response, CurrentUser.class);
                        if (currentUser.getError_code() == 0) {
                            SharedPrefrencesManager.getInstance(EditProfileActivity.this).setString(AppConstant.KEY_CURRENT_USER, response);
                            SharedPrefrencesManager.getInstance(EditProfileActivity.this).setBoolean(AppConstant.IS_LOGIN, true);
                            SharedPrefrencesManager.getInstance(EditProfileActivity.this).setString(AppConstant.KEY_LOGIN_USER_ID, currentUser.getResult().getUser_id());
                            //AppUtilis.goToActivity(EditProfileActivity.this, HomeActivity.class);
                            HomeActivity.instance.setDataSideMenu();
                            finish();
                        } else if (currentUser.getError_code() == 1) {
                            AlertUtil.showToast(EditProfileActivity.this, currentUser.getError_string());
                        }


                    } else
                        AlertUtil.showSnackBarLong(EditProfileActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callProfileApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(EditProfileActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callProfileApi();
                        }
                    });
                })
                .execute();
    }
}
