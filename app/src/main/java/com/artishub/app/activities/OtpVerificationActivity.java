package com.artishub.app.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.artishub.app.R;
import com.artishub.app.constant.AppConstant;
import com.artishub.app.constant.ServerConstants;
import com.artishub.app.databinding.ActivityOtpVerificationBinding;
import com.artishub.app.helpers.AlertUtil;
import com.artishub.app.helpers.AppUtilis;
import com.artishub.app.helpers.RestClient;
import com.artishub.app.helpers.SharedPrefrencesManager;
import com.artishub.app.model.CurrentUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.artishub.app.helpers.RestClient.ApiRequest.METHOD_POST;

public class OtpVerificationActivity extends AppCompatActivity {
    private ActivityOtpVerificationBinding binding;
    private String phone_no,otp;
    private RestClient.ApiRequest apiRequest;
    private RestClient.ApiRequest apiRequestOtp;
    CurrentUser currentUser;
    private CountDownTimer countDownTimer;
    int previousLength;
    boolean backSpace;
    private boolean exit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(getDrawable(R.drawable.bg_statusbarone));
        }
        binding = DataBindingUtil.setContentView(OtpVerificationActivity.this, R.layout.activity_otp_verification);

        //*******get Phone No of user from sharedPrefrences*****//

        currentUser=new Gson().fromJson(SharedPrefrencesManager.getInstance(OtpVerificationActivity.this).getString(AppConstant.KEY_CURRENT_USER,""),CurrentUser.class);
        phone_no=currentUser.getResult().getMobile_number();
        /*Otp Api*/
        callOtpApi();

        //**********customize Spannable text***************//
        customTextView(binding.txtResend);

        //**********call method for otp forward and backward*******//
        moveCursor();
        backSpaceMethod(binding.edtOtpFour,binding.edtOtpThree);
        backSpaceMethod(binding.edtOtpThree,binding.edtOtpTwo);
        backSpaceMethod(binding.edtOtpTwo,binding.edtOtpOne);

        //*********verify button*********//
        binding.btnVerify.setOnClickListener(view -> {
//            AlertUtil.showProgressDialog(this);
            binding.rlLoader.setVisibility(View.VISIBLE);
            AppUtilis.hideSoftKeyboard(this);
            new Handler().postDelayed(() -> {
//                AlertUtil.hideProgressDialog();
                binding.rlLoader.setVisibility(View.INVISIBLE);
                AppUtilis.goToActivity(OtpVerificationActivity.this,CompleteProfileActivity.class);
                finish();

            }, 1500);

        });

    }


   //*****************customize Spannable text**********************//
    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                getResources().getString(R.string.dont_recieve_otp));
        spanTxt.append(" " + getResources().getString(R.string.resend));

        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                AlertUtil.showToast(OtpVerificationActivity.this,"Coming Soon");

            }

            @Override
            public void updateDrawState(TextPaint ds) {

                //setColor
                ds.setColor(getResources().getColor(R.color.app_color));
                ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

//                ds.setUnderlineText(true);
                //super.updateDrawState(ds);
            }
        }, spanTxt.length() - getResources().getString(R.string.resend).length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    //************Otp verification Api**************//
    private void callOtpApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile_number",phone_no);
        params.put("method","send_otp");



        apiRequest = new RestClient.ApiRequest(this);
        apiRequest.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("forgot password")
                .setResponseListener((tag, response) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    //AlertUtil.hideProgressDialog();
                    if (response != null) {
                        try {
                            String message;
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("error_code") == 0) {
                                message = jsonObject.getString("error_string");
                                otp=jsonObject.getString("result");
                                //AlertUtil.showToast(OtpVerificationActivity.this, message);
                                /*Start timer*/
                                setOTP();
                                countDownTimer();


                            } else {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(OtpVerificationActivity.this, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        AlertUtil.showSnackBarLong(OtpVerificationActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callOtpApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(OtpVerificationActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callOtpApi();
                        }
                    });
                })
                .execute();

    }

    //*********Start Countodwn method********************//
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
               binding.txtTimer.setText(hms);
            }

            public void onFinish() {
                binding.txtTimer.setText("00:00");//set text


            }
        }.start();
    }

    private void countDownTimer() {
        int noOfMinutes = Integer.parseInt("1")*60*1000;//Convert minutes into milliseconds

        startTimer(noOfMinutes);//start countdown

    }

    //***********back of otp field**********//
    private void backSpaceMethod(final EditText eT , final EditText editText) {


        InputFilter inputFilter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end == 0 || dstart < dend) {
                    // backspace was pressed! handle accordingly
                    editText.requestFocus();
                    editText.setSelection(editText.getText().length());
                }

                return source;
            }
        };
        eT.setFilters(new InputFilter[]{inputFilter,new InputFilter.LengthFilter(1)});
    }


    //*****************Automatic move cursor from next Otp*************//
    private void moveCursor() {
        binding.edtOtpOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousLength=s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1) {
                    binding.edtOtpTwo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                backSpace = previousLength > s.length();

//                if (backSpace) {
//                    binding.edtOtpOne.requestFocus();
//                    binding.edtOtpOne.setSelection(binding.edtOtpOne.getText().length());
//
//
//                }
            }

        });
        binding.edtOtpTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousLength=s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //AppUtilis.hideSoftKeyboard(OtpVerificationActivity.this);
                if(s.length()==1) {
                    binding.edtOtpThree.requestFocus();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                backSpace = previousLength > s.length();
/*
                if (backSpace) {
                    binding.edtOtpOne.requestFocus();
                    binding.edtOtpOne.setSelection(binding.edtOtpOne.getText().length());


                }*/
            }
        });
        binding.edtOtpThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousLength=s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==1) {
                    binding.edtOtpFour.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                backSpace = previousLength > s.length();

               /* if (backSpace) {
                    binding.edtOtpTwo.requestFocus();
                    binding.edtOtpTwo.setSelection(binding.edtOtpTwo.getText().length());


                }*/
            }
        });
        binding.edtOtpFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                previousLength=s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //AppUtilis.hideSoftKeyboard(OtpVerificationActivity.this);
                //binding.edtOtpFour.setSelection(binding.edtOtpFour.getText().length());
                if(s.length()==1){
                    binding.edtOtpFour.requestFocus();
                }
                /*Call otp verification Api*/
                //callOtpVerificationApi();
            }

            @Override
            public void afterTextChanged(Editable s) {
                backSpace = previousLength > s.length();

               /* if (backSpace) {
                    binding.edtOtpThree.requestFocus();
                    binding.edtOtpThree.setSelection(binding.edtOtpThree.getText().length());

                }*/
//                if( otpSms!=null){
//                    if(otpSms.length()==4){
//
//                        userOtp=otpSms;
//                        callOtpVerificationApi();
//                    }
//                }

            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    //************Clear otp edittext*************//
    private void clearOTP() {
        binding.edtOtpOne.setText("");
        binding.edtOtpTwo.setText("");
        binding.edtOtpThree.setText("");
        binding.edtOtpFour.setText("");
        binding.edtOtpOne.requestFocus();

    }

    //*******************Set Otp temp********************//


    //**************set otp manually*******************//
    private void setOTP(){
        binding.txtMobileNo.setText(currentUser.getResult().getCountry_code()+" "+currentUser.getResult().getMobile_number());
        if(otp!=null&&!otp.equals("")) {
            String[] otpArray = otp.split("");
            binding.edtOtpOne.setText(otpArray[1]);
            binding.edtOtpTwo.setText(otpArray[2]);
            binding.edtOtpThree.setText(otpArray[3]);
            binding.edtOtpFour.setText(otpArray[4]);
            binding.edtOtpFour.requestFocus();
            binding.edtOtpOne.setEnabled(false);
            binding.edtOtpTwo.setEnabled(false);
            binding.edtOtpThree.setEnabled(false);
            binding.edtOtpFour.setEnabled(false);


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


    //************Otp verification Api**************//
    private void callOtpVerificationApi() {
        AppUtilis.hideSoftKeyboard(this);
        //AlertUtil.showProgressDialog(this);
        binding.rlLoader.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile_number",phone_no);
        params.put("method","otp_verification");
        params.put("otp",otp);
        params.put("user_id",currentUser.getResult().getUser_id());



        apiRequestOtp = new RestClient.ApiRequest(this);
        apiRequestOtp.setUrl(ServerConstants.BASE_URL)
                .setMethod(METHOD_POST)
                .setParams(params)
                .setTag("forgot password")
                .setResponseListener((tag, response) -> {
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    //AlertUtil.hideProgressDialog();
                    if (response != null) {
                        try {
                            String message;
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("error_code") == 0) {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(OtpVerificationActivity.this, message);
                                String steps=jsonObject.getString("step_status");
                                SharedPrefrencesManager.getInstance(OtpVerificationActivity.this).setString(AppConstant.STEPS,steps);
                                AppUtilis.goToActivity(OtpVerificationActivity.this,CompleteProfileActivity.class);
                                finish();

                            } else {
                                message = jsonObject.getString("error_string");
                                AlertUtil.showToast(OtpVerificationActivity.this, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else
                        AlertUtil.showSnackBarLong(OtpVerificationActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callOtpApi();
                            }
                        });

                })
                .setErrorListener((tag, errorMsg) -> {
                    //AlertUtil.hideProgressDialog();
                    binding.rlLoader.setVisibility(View.INVISIBLE);
                    AlertUtil.showSnackBarLong(OtpVerificationActivity.this, binding.getRoot(), getString(R.string.error_generic), "Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callOtpApi();
                        }
                    });
                })
                .execute();

    }
}
