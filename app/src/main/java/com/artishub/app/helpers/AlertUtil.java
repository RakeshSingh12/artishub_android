package com.artishub.app.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.artishub.app.R;


/**
 * Created by Naresh on 10/4/2016.
 */

public class AlertUtil {
    private static ProgressDialog progressDialog;
    private static ProgressBar progressBar;

    public static void showSnackBarShort(Context context, View view, String msg) {
        if (context != null) {
            Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public static void showSnackBarLong(Context context, View view, String msg, String buttonTitle, final View.OnClickListener onClickListener) {
        if (context != null) {
            Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
            snackbar.setAction(buttonTitle, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(view);
                }
            });
            snackbar.show();
        }
    }

    public static void showSnackBarLong(Context context, View view, String msg) {
        if (context != null) {
            Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public static void showToast(Context context, String msg) {
        if (context != null)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String msg) {
        if (context != null)
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    public static void showAlertDialogNotCancelable(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.show();
    }
    public static void showAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", null);
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public static void showAlertDialogWithOK(Context context, String msg, DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.app_name));
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", okListener);
        builder.show();
    }

    public static void showAlertDialog(Context context, String title, String msg, String button1, String button2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(button1, null);
        builder.setNegativeButton(button2, null);
        builder.show();
    }

    public static void showAlertDialog(Context context, String title, String msg, String button1, String button2, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(button1, okListener);
        builder.setNegativeButton(button2, cancelListener);
        builder.show();
    }

    public static void showAlertDialog(Context context, String title, String msg, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", okListener);
        builder.setNegativeButton("Cancel", cancelListener);
        builder.show();
    }

    public static void showProgressDialog(final Context context) {
        if (context != null) {
            if (progressDialog != null && progressDialog.isShowing()) {

            } else {
                progressDialog = new ProgressDialog(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                TextView titleView = (TextView) progressDialog.findViewById(R.id.title);
                titleView.setVisibility(View.GONE);
            }
        }
    }

    public static void showProgressDialogProgress(final Context context, final String title) {
        if (context != null) {
            if (progressDialog != null && progressDialog.isShowing()) {

            } else {
                progressDialog = new ProgressDialog(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                TextView titleView = (TextView) progressDialog.findViewById(R.id.title);
                titleView.setText(title);
            }
        }
    }

    public static void setProgress(Context context, final int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
    }


    public static void showProgressDialog(final Context context, String title) {
        if (progressDialog != null && progressDialog.isShowing()) {

        } else {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(title);
            progressDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
