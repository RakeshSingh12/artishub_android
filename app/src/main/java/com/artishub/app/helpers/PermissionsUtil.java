package com.artishub.app.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by king on 30/11/16.
 */
public class PermissionsUtil {
    static PermissionListener permissionListener;
    static Activity context;
    static String tag;
    static int  requestCode = 1001;

    public static String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static String STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static String CAMERA = Manifest.permission.CAMERA;
    public static String READ_SMS = Manifest.permission.READ_SMS;
    public static String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    public static String ACCOUNTS = Manifest.permission.GET_ACCOUNTS;
    public static String AUDIO_RECODER = Manifest.permission.RECORD_AUDIO;
    public static String SEND_SMS = Manifest.permission.SEND_SMS;


    static String storagePermInfoMsg = "App needs this permission to store files on phone's storage. Are you sure you want to deny this permission ?";
    static String storagePermErrorMsg = "Storage permission denied. You can enable permission from settings";

    static String contactsPermInfoMsg = "App needs this permission to access phone contacts. Are you sure you want to deny this permission ?";
    static String contactsPermErrorMsg = "Contacts permission denied. You can enable permission from settings";

    static String smsPermInfoMsg = "App needs this permission to receive/read SMS for auto detection of OTP. Are you sure you want to deny this permission ?";
    static String smsPermErrorMsg = "SMS permission denied. You can enable permission from settings";

    static String accountsPermInfoMsg = "App needs this permission to access Google Account on phone. Are you sure you want to deny this permission ?";
    static String accountsPermErrorMsg = "Contacts permission denied. You can enable permission from settings";

    static String cameraPermInfoMsg = "App needs this permission to capture photos using phone's camera. Are you sure you want to deny this permission ?";
    static String cameraPermErrorMsg = "Camera permission denied. You can enable permission from settings";

    static String calendarPermInfoMsg = "App needs this permission to access phone's calendar. Are you sure you want to deny this permission ?";
    static String calendarPermErrorMsg = "Calendar permission denied. You can enable permission from settings";

    static String locationPermInfoMsg = "App needs this permission to access your location. Are you sure you want to deny this permission ?";
    static String locationPermErrorMsg = "Location permission denied. You can enable permission from settings";

    static String audioRecoderPermInfoMsg = "App needs this permission to access your phone's microphone. Are you sure you want to deny this permission ?";
    static String audioRecoderPermErrorMsg = "Microphone permission denied. You can enable permission from settings";

    static String sendSmsPermInfoMsg = "App needs this permission to send SMS for invite friends. Are you sure you want to deny this permission ?";
    static String sendSmsPermErrorMsg = "Send SMS permission denied. You can enable permission from settings";



    public static void askPermission(final Activity context, final String permission, final PermissionListener permissionListener) {
        PermissionsUtil.context = context;
        PermissionsUtil.permissionListener = permissionListener;

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
            return;
        } else {
            permissionListener.onPermissionResult(true);
        }
    }

    public static void askPermissions(final Activity context, final String permission1, final String permission2, final PermissionListener permissionListener) {
        PermissionsUtil.context = context;
        PermissionsUtil.permissionListener = permissionListener;

        if (ContextCompat.checkSelfPermission(context, permission1) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, permission2) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission1,permission2}, requestCode);
            return;
        } else {
            permissionListener.onPermissionResult(true);
        }
    }

    public static void onRequestPermissionsResult(final int requestCode, String[] permissions, int[] grantResults) {
        final String permission = permissions[0];
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionListener.onPermissionResult(true);
        }
        else  if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            permissionListener.onPermissionResult(true);

        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission Required");
                if (permission.equals(STORAGE))
                    alertBuilder.setMessage(storagePermInfoMsg);
                else if (permission.equals(READ_SMS))
                    alertBuilder.setMessage(smsPermInfoMsg);
                else if (permission.equals(RECEIVE_SMS))
                    alertBuilder.setMessage(smsPermInfoMsg);
                else if (permission.equals(ACCOUNTS))
                    alertBuilder.setMessage(accountsPermInfoMsg);
                else if (permission.equals(READ_CALENDAR))
                    alertBuilder.setMessage(calendarPermInfoMsg);
                else if (permission.equals(WRITE_CALENDAR))
                    alertBuilder.setMessage(calendarPermInfoMsg);
                else if (permission.equals(CAMERA))
                    alertBuilder.setMessage(cameraPermInfoMsg);
                else if (permission.equals(LOCATION))
                    alertBuilder.setMessage(locationPermInfoMsg);
                else if (permission.equals(READ_CONTACTS))
                    alertBuilder.setMessage(contactsPermInfoMsg);
                else if (permission.equals(WRITE_CONTACTS))
                    alertBuilder.setMessage(contactsPermInfoMsg);
                else if (permission.equals(AUDIO_RECODER))
                    alertBuilder.setMessage(audioRecoderPermInfoMsg);
                else if (permission.equals(SEND_SMS))
                    alertBuilder.setMessage(sendSmsPermInfoMsg);

                alertBuilder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
                    }
                });
                alertBuilder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        permissionListener.onPermissionResult(false);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            } else {
                if (permission.equals(STORAGE))
                    Toast.makeText(context, storagePermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(READ_SMS))
                    Toast.makeText(context, smsPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(RECEIVE_SMS))
                    Toast.makeText(context, smsPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(ACCOUNTS))
                    Toast.makeText(context, accountsPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(READ_CALENDAR))
                    Toast.makeText(context, calendarPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(WRITE_CALENDAR))
                    Toast.makeText(context, calendarPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(CAMERA))
                    Toast.makeText(context, cameraPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(LOCATION))
                    Toast.makeText(context, locationPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(READ_CONTACTS))
                    Toast.makeText(context, contactsPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(WRITE_CONTACTS))
                    Toast.makeText(context, contactsPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(AUDIO_RECODER))
                    Toast.makeText(context, audioRecoderPermErrorMsg, Toast.LENGTH_LONG).show();
                else if (permission.equals(SEND_SMS))
                    Toast.makeText(context, sendSmsPermErrorMsg, Toast.LENGTH_LONG).show();

                permissionListener.onPermissionResult(false);
            }

        }
        return;
    }

    public interface PermissionListener {
        void onPermissionResult(boolean isGranted);
    }
}