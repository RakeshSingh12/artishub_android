package com.artishub.app.fragment;


import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.activities.CompleteProfileActivity;
import com.artishub.app.activities.ForgotActivity;
import com.artishub.app.activities.HomeActivity;
import com.artishub.app.activities.LoginSignupActivity;
import com.artishub.app.activities.OtpVerificationActivity;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.MsgConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.FragmentLoginBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.AppValidator;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private View v;
    private RestClient.ApiRequest apiRequest;
    private String emailPhone, password;
    private CurrentUser currentUser;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        v = binding.getRoot();


        //*********Click on Forgot Password*******//
        binding.btnLogin.setOnClickListener(view -> {
            if (validData()) {
                emailPhone = binding.edtEmail.getText().toString();
                password = binding.edtPassword.getText().toString();
                callLoginApi();
            }
        });

        //***************Click on Forgot Password*************//
        binding.txtForgotPassword.setOnClickListener(view -> {
            AppUtilis.goToActivity(LoginSignupActivity.instance, ForgotActivity.class);
        });

        customTextView(binding.txtSignUp);
        return v;

    }

    //***********method to make custom TextView**********************//
    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                getResources().getString(R.string.dont_have_account));
        spanTxt.append(" " + getResources().getString(R.string.register_here));

        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                flipCard();

            }

            @Override
            public void updateDrawState(TextPaint ds) {

                //setColor
                ds.setColor(getResources().getColor(R.color.app_color));
//                ds.setUnderlineText(true);
                ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

                //super.updateDrawState(ds);
            }
        }, spanTxt.length() - getResources().getString(R.string.register_here).length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }


    //******************FlipCard Animation**************************//
    private void flipCard() {
        LoginSignupActivity.isShowingBackLayout = true;

        getFragmentManager().beginTransaction()
                // Replace the default fragment animations with animator resources representing
                // rotations when switching to the back of the card, as well as animator
                // resources representing rotations when flipping back to the front (e.g. when
                // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                // Replace any fragments currently in the container view with a fragment
                // representing the next page (indicated by the just-incremented currentPage
                // variable).
                .replace(R.id.container, new SignUpFragment())

                // Add this transaction to the back stack, allowing users to press Back
                // to get to the front of the card.
                .addToBackStack(null)
                .commit();
    }


    //********************Validation of Data*********************//
    private boolean validData() {
        if (!AppValidator.isValidEmailPhone(LoginSignupActivity.instance, binding.edtEmail, MsgConstant.EMAIL_PHONE_ERROR_VOID)) {
            return false;
        } else if (!AppValidator.isValidPassword(LoginSignupActivity.instance, binding.edtPassword, MsgConstant.PASSWORD_ERROR_VOID)) {
            return false;
        }

        return true;
    }

    //****************Login password Api***************//
    private void callLoginApi() {
        AppUtilis.hideSoftKeyboard(LoginSignupActivity.instance);
        //AlertUtil.showProgressDialog(LoginSignupActivity.instance);
        LoginSignupActivity.instance.binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("username", emailPhone);
        params.put("method", "signin");
        params.put("password", password);


        apiRequest = new RestClient.ApiRequest(LoginSignupActivity.instance);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("login")
                .setResponseListener((tag, response) -> {
                    // AlertUtil.hideProgressDialog();
                    LoginSignupActivity.instance.binding.rlLoader.setVisibility(View.INVISIBLE);

                    if (response != null) {
                        currentUser = new Gson().fromJson(response, CurrentUser.class);
                        if (currentUser.getError_code() == 0) {
                            SharedPrefrencesManager.getInstance(LoginSignupActivity.instance).setString(AppConstant.STEPS, currentUser.getResult().getStep_status());
                            SharedPrefrencesManager.getInstance(LoginSignupActivity.instance).setString(AppConstant.KEY_CURRENT_USER, response);
                            SharedPrefrencesManager.getInstance(LoginSignupActivity.instance).setString(AppConstant.KEY_LOGIN_USER_ID, currentUser.getResult().getUser_id());
                            if (currentUser.getResult().getStep_status().equals("1")) {

                                AppUtilis.goToActivity(LoginSignupActivity.instance, OtpVerificationActivity.class);
                                LoginSignupActivity.instance.finish();

                            } else if (currentUser.getResult().getStep_status().equals("2")) {

                                AppUtilis.goToActivity(LoginSignupActivity.instance, CompleteProfileActivity.class);
                                LoginSignupActivity.instance.finish();

                            } else if (currentUser.getResult().getStep_status().equals("3")) {

                                SharedPrefrencesManager.getInstance(LoginSignupActivity.instance).setBoolean(AppConstant.IS_LOGIN, true);
                                AppUtilis.goToActivity(LoginSignupActivity.instance, HomeActivity.class);
                                LoginSignupActivity.instance.finish();
                            }

                        } else if (currentUser.getError_code() == 1) {
                            AlertUtil.showToast(LoginSignupActivity.instance, currentUser.getError_string());
                        }
                    } else
                        AlertUtil.showSnackBarLong(LoginSignupActivity.instance, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callLoginApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    LoginSignupActivity.instance.binding.rlLoader.setVisibility(View.INVISIBLE);
                    //AlertUtil.hideProgressDialog();
                    AlertUtil.showSnackBarLong(LoginSignupActivity.instance, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callLoginApi();
                        }
                    });
                })
                .execute();

    }


}
