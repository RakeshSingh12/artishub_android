package com.artishub.app.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artishub.app.R;
import com.artishub.app.activities.CountryPickerActivity;
import com.artishub.app.activities.HomeActivity;
import com.artishub.app.activities.LoginSignupActivity;
import com.artishub.app.activities.OtpVerificationActivity;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.MsgConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.FragmentSignUpBinding;
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
public class SignUpFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {
    private FragmentSignUpBinding binding;
    private View view;
    private String email,phoneNo,password,countryCode="+234";
    private RestClient.ApiRequest apiRequest;
    private CurrentUser currentUser;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up, container, false);
       view=binding.getRoot();
        LocalBroadcastManager.getInstance(LoginSignupActivity.instance).registerReceiver(mMessageReceiver,
                new IntentFilter("CountryCodeSelection"));

       binding.btnSignup.setOnClickListener(view1 -> {
          if(checkData()){
           email=binding.edtEmail.getText().toString();
           phoneNo=binding.edtPhone.getText().toString();
           password=binding.edtPassword.getText().toString();
           callSignupApi();
          }
       });

       customTextView(binding.txtLogin);

       binding.txtCountryCode.setOnClickListener(view1 -> {
           AppUtilis.goToActivity(LoginSignupActivity.instance, CountryPickerActivity.class);
       });
        return view;
    }


    @Override
    public void onBackStackChanged() {
        LoginSignupActivity.isShowingBackLayout = (LoginSignupActivity.instance.getFragmentManager().getBackStackEntryCount() > 0);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            countryCode = intent.getStringExtra("position");
            binding.txtCountryCode.setText(countryCode);

        }
    };

    private void flipCard() {
        if (LoginSignupActivity.isShowingBackLayout) {
            getFragmentManager().popBackStack();
            return;
        }
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
                .replace(R.id.container, new LoginFragment())

                // Add this transaction to the back stack, allowing users to press Back
                // to get to the front of the card.
                .addToBackStack(null)
                .commit();
    }

    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                getResources().getString(R.string.already_account));
        spanTxt.append(" " + getResources().getString(R.string.login_here));

        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                flipCard();

            }

            @Override
            public void updateDrawState(TextPaint ds) {

                //setColor
                ds.setColor(getResources().getColor(R.color.app_color));
                ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
//                ds.setUnderlineText(true);
                //super.updateDrawState(ds);
            }
        }, spanTxt.length() - getResources().getString(R.string.login_here).length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }


    //***************check validation of data**************//
    private boolean checkData() {
         if (!AppValidator.isValidEmail(LoginSignupActivity.instance, binding.edtEmail, MsgConstant.EMAIL_ERROR_VOID)) {
            return false;
        } else if (!AppValidator.isValidMobile(LoginSignupActivity.instance, binding.edtPhone, MsgConstant.PHONE_ERROR_VOID)) {
            return false;
        } else if (!AppValidator.isValidPassword(LoginSignupActivity.instance, binding.edtPassword, MsgConstant.PASSWORD_ERROR_VOID)) {
            return false;
        } else if (!AppValidator.isValidPassword(LoginSignupActivity.instance, binding.edtConfirmPassword, MsgConstant.CONFIRM_PASSWORD_ERROR_VOID)) {
            return false;
        } else if (!binding.edtPassword.getText().toString().equals(binding.edtConfirmPassword.getText().toString())) {
            AlertUtil.showToast(LoginSignupActivity.instance, "Password and Confirm Password should be Same");
            return false;
        }


        return true;
    }


    //****************Signup password Api***************//
    private void callSignupApi() {
        AppUtilis.hideSoftKeyboard(LoginSignupActivity.instance);
        LoginSignupActivity.instance.binding.rlLoader.setVisibility(View.VISIBLE);
        //AlertUtil.showProgressDialog(LoginSignupActivity.instance);
        HashMap<String, String> params = new HashMap<>();
        params.put("email",email);
        params.put("method","signup");
        params.put("password",password);
        params.put("mobile_number",phoneNo);
        params.put("country_code",countryCode);



        apiRequest = new RestClient.ApiRequest(LoginSignupActivity.instance);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("Signup")
                .setResponseListener((tag, response) -> {
                    LoginSignupActivity.instance.binding.rlLoader.setVisibility(View.INVISIBLE);

                    //AlertUtil.hideProgressDialog();
                    if (response != null) {
                        currentUser=new Gson().fromJson(response,CurrentUser.class);
                        if(currentUser.getError_code()==0){
                            SharedPrefrencesManager.getInstance(LoginSignupActivity.instance).setString(AppConstant.STEPS,currentUser.getResult().getStep_status());
                            SharedPrefrencesManager.getInstance(LoginSignupActivity.instance).setString(AppConstant.KEY_CURRENT_USER,response);
                            SharedPrefrencesManager.getInstance(LoginSignupActivity.instance).setString(AppConstant.KEY_LOGIN_USER_ID,currentUser.getResult().getUser_id());
                            AppUtilis.goToActivity(LoginSignupActivity.instance, OtpVerificationActivity.class);
                            LoginSignupActivity.instance.finish();
                        }else if(currentUser.getError_code()==1){
                            AlertUtil.showToast(LoginSignupActivity.instance,currentUser.getError_string());
                        }


                    } else
                        AlertUtil.showSnackBarLong(LoginSignupActivity.instance, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callSignupApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    LoginSignupActivity.instance.binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(LoginSignupActivity.instance, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callSignupApi();
                        }
                    });
                })
                .execute();

    }


}
