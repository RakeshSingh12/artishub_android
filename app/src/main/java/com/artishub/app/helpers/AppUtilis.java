package com.artishub.app.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;


import com.artishub.app.R;
import com.artishub.app.listener.GetDob;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppUtilis {

    public static Uri mImageUri;
    public static ProgressDialog pd;
    static GetDob getDob;


    /**
     * To redirect from one activity to another activity
     *
     * @param context
     * @param act
     */
    public static void goToActivity(Context context, Class<?> act) {
        Intent i = new Intent(context, act);
        context.startActivity(i);
    }

    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            //Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }
    public static  String utcToLocal(String s) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df.parse(s);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(date);
        return formattedDate;
    }

    public static void clearAllgoToActivity(Context context, Class<?> act) {
        Intent i = new Intent(context, act);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void setGradientTextColor(Context context, TextView textView) {
        int[] color = {ContextCompat.getColor(context, R.color.gradient_start), ContextCompat.getColor(context, R.color.gradient_end)};
        float[] size = {0, 1};

        Rect bounds = new Rect();
        Paint textPaint = textView.getPaint();
        textPaint.getTextBounds(textView.getText().toString(), 0, textView.getText().toString().length(), bounds);
        int width = bounds.width();
        textView.getPaint().setShader(new LinearGradient(0, 0, width, (float) (textView.getLineHeight() * 1.10), color, size, Shader.TileMode.CLAMP));

    }

    public static String getColoredSpanned(String text, String color) {
        String input = "<b><font color=" + color + ">" + text + "</font></b>";
        return input;
    }





    private static boolean contactAdd(JSONArray contacts, JSONObject contact) throws JSONException {
        boolean add = true;
        for (int i =0;i<contacts.length();i++){
            if (contacts.get(i)==contact){
                add = false;
                break;
            }
        }
        return add;
    }

    /**
     * To format the given date in a specific format
     *
     * @param year  Year
     * @param month Month
     * @param day   Day
     * @return String date
     */
    public static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    /**
     * To check that email is valid or not.
     *
     * @param email Email String on which pattern will check.
     * @return True if entered email is valid and False if entered email is
     * invalid
     */
    public static boolean isEmailValid(final CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }




    public static String getString(final Context context, final int resId) {
        return ((Activity) context).getResources().getString(resId);
    }


    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        // should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }



    public static String setTextDouble(String text) {
        if (text != null && !text.equals("null") && !text.equals("")) {
            return String.format("%.2f", Double.parseDouble(text));
        }
        return "0.0";
    }

    public static String decimalTwoPlaces(String amount) {
        String decimalValue = "";
        if (amount != null & !amount.equals("")) {
            decimalValue = String.format("%.2f", Double.parseDouble(amount));
        }
        return decimalValue;
    }

    public static String arabicToNumaric(String amount) {
        String decimalValue = "";
        if (amount != null & !amount.equals("")) {


            decimalValue = amount.toString().replaceAll("٠", "0")
                    .replaceAll("٫", ".")
                    .replaceAll("١", "1")
                    .replaceAll("٢", "2")
                    .replaceAll("٣", "3")
                    .replaceAll("٤", "4")
                    .replaceAll("٥", "5")
                    .replaceAll("٦", "6")
                    .replaceAll("٧", "7")
                    .replaceAll("٨", "8")
                    .replaceAll("٩", "9");


        }
        return decimalValue;
    }

    @SuppressLint("NewApi")
    public static int[] getScreenSpec(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return new int[]{width, height};
    }


    public static Bitmap grabImage(final Context context, final Uri mImageUri) {
        context.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = context.getContentResolver();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(cr,
                    mImageUri);
        } catch (Exception e) {
            //   Toast.makeText(context, context.getResources().context.getString(R.string.failed_load), Toast.LENGTH_SHORT).show();
        }
        return bitmap;
    }


    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    public static void showSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {

        }
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.JPEG, 70, outputStream);
            return outputStream.toByteArray();
        } else {
            return null;
        }
    }

    public static String encodeImage(byte[] imageByteArray) {
        String encodeString = null;
        if (imageByteArray != null) {
            encodeString = Base64
                    .encodeToString(imageByteArray, Base64.DEFAULT);
        }
        return encodeString;
    }

    public static float dipToPixels(Context context, float f) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f,
                metrics);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("HEIGHT", result + "");
        return result;
    }


    public static String getCountryNameFromLocale() {
        Locale defaultLocale = Locale.getDefault();
        String countryName = defaultLocale.getDisplayCountry();
        Log.e("COUNTRY", countryName);
        return countryName;
    }

    public static void shareData(Context context, String text, Bitmap path) {
        try {
            String path1 = MediaStore.Images.Media.insertImage(context.getContentResolver(), path, "", null);
            Uri screenshotUri = Uri.parse(path1);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            shareIntent.setType("image/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Share images..."));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendIntent(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        Intent mailer = Intent.createChooser(intent, null);
        context.startActivity(mailer);
    }

    public static void shareApplication(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.techugo.prozata&hl=en");
        context.startActivity(Intent.createChooser(intent, "Share with"));
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight, InputStream is) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(is, null, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static String getRealPathFromURI(Context context, Uri contentUri) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(contentUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            return cursor.getString(columnIndex);
        } catch (Exception e) {
            return contentUri.getPath();
        }

    }

    public static String getDateFromTimeStamp(long timestamp) {
        try {
            DateFormat df = DateFormat.getTimeInstance();
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            String gmtTime = df.format(new Date());

            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            String date = dateFormatGmt.format(new Date());
            return date;
        } catch (Exception e) {
        }
        return "";
    }

    public static String getDateFromTimeStampComment(long timestamp) {
        try {
//            DateFormat df = DateFormat.getTimeInstance();
//            df.setTimeZone(TimeZone.getTimeZone("UTC"));
//            String gmtTime = df.format(new Date());
            Calendar aGMTCalendar = Calendar.getInstance();
            Date phoneTimeUTC = aGMTCalendar.getTime();
            SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));
            String date = dateFormatGmt.format(phoneTimeUTC);
            return date;
        } catch (Exception e) {
        }
        return "";
    }


    public static void showAlert(Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if context button is clicked, close
                        // current activity
                        dialog.dismiss();
                    }


                });

        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

   /* public static LatLngBounds getLatLngBounds(LatLng center) {
        double radiusDegrees = 1.0;
        LatLng northEast = new LatLng(center.latitude + radiusDegrees, center.longitude + radiusDegrees);
        LatLng southWest = new LatLng(center.latitude - radiusDegrees, center.longitude - radiusDegrees);
        LatLngBounds bounds = LatLngBounds.builder()
                .include(northEast)
                .include(southWest)
                .build();

        return bounds;
    }*/


    public static Bitmap changeSize(BitmapDrawable bitmapDrawable, int width, int height) {
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        return smallMarker;
    }


    public static String updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        String time = hours + ":" + minutes + " " + timeSet;
        return time;

    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder result = null;
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            result = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                result.append(line).append('\n');
            }
            return result.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public static void showStatus(TextView textView, ListView listView, String value, boolean isShow) {
        if (textView != null && listView != null && value != null) {
            if (isShow) {
                textView.setVisibility(View.VISIBLE);
                textView.setText(value);
                listView.setVisibility(View.GONE);
            } else {
                textView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        }
    }


    public static String formatDateTime(String time, String inputPattern, String outputPattern) {

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
      //  outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String[] getCounterTime(long different) {
        String[] counttimearray = null;
        if (different > 0) {
            // Calendar calender = Calendar.getInstance();
            counttimearray = printDifference(different);
        } else {
            counttimearray = new String[]{"0", "0", "0", "0"};
        }
        return counttimearray;

    }

    public static String[] printDifference(long different) {
        String[] counttime = new String[4];

        //milliseconds

        /*System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);*/

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

      /*  System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);*/
        counttime[0] = String.valueOf(elapsedDays);
        counttime[1] = String.valueOf(elapsedHours);
        counttime[2] = String.valueOf(elapsedMinutes);
        counttime[3] = String.valueOf(elapsedSeconds);

        return counttime;
    }


    public static String getActivityName(Activity activity) {

        if (activity != null) {
            return activity.getClass().getSimpleName();
        } else {
            return "";
        }

    }

    public static void openDatePicker(Context context, int day, int month, int year, final GetDob listeners) {
        // Get Current Date
        int mYear,mMonth,mDay;
        if(year==0){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        else {
            mYear = year;
            mMonth = month;
            mDay = day;
        }
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        listeners.getDobSuccess(dayOfMonth, monthOfYear + 1, year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select Date of birth");
       // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-(((long)3.78692e+11)));
        datePickerDialog.show();


    }
    public static void openFutureDatePicker(Context context, final GetDob listeners, int year, int month, int day) {

        int mYear,mMonth,mDay;
        if(year==0){
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }
        else {
            mYear = year;
            mMonth = month;
            mDay = day;
        }



        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        listeners.getDobSuccess(dayOfMonth, monthOfYear + 1, year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();


    }

    public static void openTimePicker(Context context, final GetDob listeners, int hr, int min) {
        int hour,minute;
        if(hr==0) {
            //if not selected
            Calendar mcurrentTime = Calendar.getInstance();
            hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            minute = mcurrentTime.get(Calendar.MINUTE);
        }
        else{
            //if already selected
            hour=hr;
            minute=min;
        }
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                listeners.getTimeSuccess(selectedHour, selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");

        mTimePicker.show();
    }

    public static String getDateFromMillis(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public static String getCurrentDate(String dateFormat) {

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        String date = dateFormatGmt.format(new Date());

        return date;


    }



    public static String getTwoMonthBeforeDate(int month) {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -month);
        c.add(Calendar.DAY_OF_MONTH, +1);

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        String m, d;

        if (mMonth < 10) {
            m = "0" + mMonth + "";
        } else {
            m = mMonth + "";
        }

        if (mDay < 10) {
            d = "0" + mDay + "";
        } else {
            d = mDay + "";
        }


        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(mYear + "-" + m + "-" + d);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }

    public static void changeBackGround(TextView textView, android.graphics.drawable.Drawable rid, Context context, int a) {
        textView.setBackground(rid);
        textView.setTextColor(a);


    }


    public static String getFromDate(int days) {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, +1);
        c.add(Calendar.DAY_OF_MONTH, -days);

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        String m, d;

        if (mMonth < 10) {
            m = "0" + mMonth + "";
        } else {
            m = mMonth + "";
        }

        if (mDay < 10) {
            d = "0" + mDay + "";
        } else {
            d = mDay + "";
        }


        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(mYear + "-" + m + "-" + d);
            date.setHours(12);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;


    }


    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }


    public static void setProfileImagePicasso(Context context, ImageView view, String path) {
        if (path.length() != 0) {
            Picasso.with(context)
                    .load(path)
                    .placeholder(R.drawable.user)
                    .fit()
                    .centerCrop()
                    .into(view);
        } else {
            Picasso.with(context)
                    .load(R.drawable.user)
                    .placeholder(R.drawable.user)
                    .fit()
                    .centerCrop()
                    .into(view);
        }
    }

    public static void setImagePicasso(Context context, ImageView view, String path) {
        if (path.length() != 0) {
            Picasso.with(context)
                    .load(path)
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .centerCrop()
                    .into(view);
        } else {
            Picasso.with(context)
                    .load(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .centerCrop()
                    .into(view);
        }
    }


    public static String decodeBase64String(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {

            return decodedString;
        }
    }


    public static String extractYTId(String ytUrl) {
        String vId = null;
        if (ytUrl.contains("embed")) {
            String[] param = ytUrl.split("/");
            vId = param[param.length - 1];
        } else {

            Pattern pattern = Pattern.compile(
                    "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?",
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(ytUrl);
            if (matcher.matches()) {
                vId = matcher.group(1);
            }
        }
        return vId;
    }

    public static boolean checkJson(String str) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            return true;
        } else {
            return false;
        }
    }
    public static String formateMilliSeccond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        //      return  String.format("%02d Min, %02d Sec",
        //                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
        //                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
        //                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));

        // return timer string
        return finalTimerString;
    }

}
