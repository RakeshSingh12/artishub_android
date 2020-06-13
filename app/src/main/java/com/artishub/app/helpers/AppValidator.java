package com.artishub.app.helpers;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.File;


public class AppValidator {

    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String NAME_REGEX1 = "^[_A-Za-z0-9-\\+]";
    public static final String NAME_REGEX = "^[A-Za-z0-9\\s]{1,}[\\.]{0,1}[A-Za-z0-9\\s]{0,}$";
    public static final String CHAR_REGEX = ".*[a-zA-Z]+.*";
    public static final String ONLY_CHAR_REGEX = "^[a-zA-Z ]*$";
    public static final String USERNAME_REGEX="^[a-z]+[a-z0-9._-]{4,14}$";
    public static final String MOBILE_REGEX = "\\d{10}";
    public static final String MOBILE_REGEX_TEST = "\\d{10}|.{11}";
    public static final String YEAR_REGEX = "\\d{4}";
    public static final String PINCODE_REGEX = "^([1-9])([0-9]){5}$";
    public static final String VEHICLE_REGEX = "^[A-Z]{2} [0-9]{2} [A-Z]{2} [0-9]{4}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_])(?=\\S+$).{6,}$";//.{8,}
    public static final String[] IMAGE_EXTENSIONS = new String[]{"jpg", "jpeg", "png"};

    public static boolean isValidEmail(Context context, EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {

            // editText.setError(msg);
            AlertUtil.showToast(context, msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().matches(EMAIL_REGEX)) {
            return true;
        } else {
            AlertUtil.showToast(context, "Invalid Email");
            //editText.setError("Invalid Email");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
    }

    public static boolean isValidEmailPhone(Context context, EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {

            // editText.setError(msg);
            AlertUtil.showToast(context, msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().matches(EMAIL_REGEX)) {
            return true;

        }else if(editText.getText().toString().matches(MOBILE_REGEX_TEST)) {
            return  true;
        }

        else {
            AlertUtil.showToast(context, "Invalid Email or Phone number");
            //editText.setError("Invalid Email");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
    }


  /*  public static boolean isValidPassword(EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {
            editText.setError(msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().trim().matches(PASSWORD_REGEX))
            return true;
        else {
            editText.setError("Password should contain  Alphanumeric.");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
    }*/


    public static boolean isValidPassword(Context context, EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {
            AlertUtil.showToast(context, msg);
            //editText.setError(msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().length() >= 6)
            return true;
        else {
            AlertUtil.showToast(context, "Password must contain at least 6 characters.");
            // editText.setError("Password should contain  alphanumeric with one caps and special character.");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
    }

    public static boolean isValidName(Context context, EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {

            // editText.setError(msg);
            AlertUtil.showToast(context, msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().matches(ONLY_CHAR_REGEX))
            return true;
        else {
            // editText.setError("invalid name");
            AlertUtil.showToast(context, "Invalid Name");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
    }

    public static boolean isValidMobile(Context context, EditText editText, String msg) {


        if (editText.getText().toString().trim().equals("")) {

            //editText.setError(msg);
            AlertUtil.showToast(context,msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().matches(MOBILE_REGEX_TEST))
            return true;
        else {
            AlertUtil.showToast(context,"Invalid Mobile No");
            //editText.setError("invalid mobile");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }

    }

    public static boolean isValidPincode(EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {

            editText.setError(msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().matches(PINCODE_REGEX))
            return true;
        else {
            editText.setError("invalid pincode");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
    }


    public static boolean isValidAddress(EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {

            editText.setError(msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().matches(NAME_REGEX))
            return true;
        else {
            editText.setError("invalid address");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
    }

    public static boolean isOnlyChars(Context context, EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {

            //editText.setError(msg);
            AlertUtil.showToast(context,msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.setFocusable(true);
            return false;
        } else if (editText.getText().toString().matches(ONLY_CHAR_REGEX))
            return true;
        else {
            AlertUtil.showToast(context,"invalid name");
            //editText.setError("invalid name");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }

    }

    public static boolean isOnlyChar(Context context, EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {

            //editText.setError(msg);
            AlertUtil.showToast(context,msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.setFocusable(true);
            return false;
        } else if (editText.getText().toString().matches(USERNAME_REGEX))
            return true;
        else {

            if(editText.getText().toString().charAt(0)<97&&editText.getText().toString().charAt(0)<122)
            {
                AlertUtil.showAlertDialog(context,"GetZApp","First character should be a letter .");

            }
            else {
                AlertUtil.showAlertDialog(context, "GetZApp", "Please enter 5-15 characters for username.");
            } //editText.setError("invalid name");
            return false;
        }

    }


    public static boolean isValidVehicleNo(EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {

            editText.setError(msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        } else if (editText.getText().toString().matches(VEHICLE_REGEX))
            return true;
        else {
            editText.setError("invalid vehicle no.");
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
    }


    public static boolean isValidImage(File file) {
        for (String extensions : IMAGE_EXTENSIONS) {
            if (file.getName().toLowerCase().endsWith(extensions))
                return true;
        }
        return false;
    }

    public static boolean isValidYear(String data) {
        if (data.matches(YEAR_REGEX))
            return true;
        else
            return false;
    }


    public static boolean isValid(Context context, EditText editText, String msg) {
        if (editText.getText().toString().trim().equals("")) {
            AlertUtil.showToast(context, msg);
            // editText.setError(msg);
            editText.addTextChangedListener(new RemoveErrorEditText(editText));
            editText.requestFocus();
            return false;
        }
        return true;
    }


    public static class RemoveErrorEditText implements TextWatcher {

        private EditText editText;


        public RemoveErrorEditText(EditText edittext) {
            this.editText = edittext;

        }

        @Override
        public void afterTextChanged(Editable s) {

            editText.setError(null);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

    }


}
