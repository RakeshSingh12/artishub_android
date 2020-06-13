package com.artishub.app.helpers;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by qamar on 5/7/17.
 */

public class DateTimeCalculator {

    public static String getDaysAgoFormat(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = "";
        try {
            Date myDate = simpleDateFormat.parse(date);
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            formattedDate = simpleDateFormat.format(myDate);
            Log.e("FORMATEDDATE", formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String dateTime[] = formattedDate.split(" ");
        String dateStr = dateTime[0];
        String timeStr = dateTime[1];
        String hour = timeStr.split(":")[0];
        String minute = timeStr.split(":")[1];
        String year1 = dateStr.split("-")[0];
        String month = dateStr.split("-")[1];
        String day = dateStr.split("-")[2];
        long selectedTimeInMillis;
        String strDay = "";
        DateTime selectedTime = new DateTime(Integer.parseInt(year1), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute));
        selectedTimeInMillis = selectedTime.getMillis();
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(selectedTimeInMillis);//Set to Epoch time
        DateTime now = new DateTime();
        DateTime now1 = new DateTime();
        Period period = new Period(selectedTime, now1);
        Log.e("days since epoch: ", "" + period.getDays());
        Log.e("weeks since epoch: ", "" + period.getWeeks());
        Log.e("months since epoch: ", "" + period.getMonths());
        Log.e("hours since epoch: ", "" + period.getHours());
        Log.e("minutes since epoch: ", "" + period.getMinutes());
        Log.e("Sec since epoch: ", "" + period.getSeconds());
        if (period.getMonths() == 0 && period.getWeeks() == 0 && period.getDays() == 0 && period.getHours() == 0 && period.getMinutes() == 0) {
            strDay = period.getSeconds() + " sec ago";
        } else if (period.getMonths() == 0 && period.getWeeks() == 0 && period.getDays() == 0 && period.getHours() == 0) {
            strDay = period.getMinutes() + " min ago";
        } else if (period.getMonths() == 0 && period.getWeeks() == 0 && period.getDays() == 0) {
            strDay = period.getHours() + " hour ago";
        } else if (period.getMonths() == 0 && period.getWeeks() == 0) {
            strDay = period.getDays() + " day ago";
        } else if (period.getMonths() == 0) {
            strDay = period.getWeeks() + " week ago";
        } else {
            strDay = period.getMonths() + " months ago";
        }

        return strDay;
    }
    public static long getDiff(String startTime, String endtime) {

        Date startDate, endDate;
        long diff=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            startDate = sdf.parse(startTime.trim());
            endDate = sdf.parse(endtime.trim());
            if (startDate != null && endDate != null)
                diff = endDate.getTime() - startDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }
}
