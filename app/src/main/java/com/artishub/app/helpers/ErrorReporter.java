/*
 * Added by Junaid.
 */
package com.artishub.app.helpers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The Class ErrorReporter.
 */
public class ErrorReporter implements Thread.UncaughtExceptionHandler {

    /**
     * The Version name.
     */
    String VersionName;

    /**
     * The build number.
     */
    String buildNumber;

    /**
     * The Package name.
     */
    String PackageName;

    /**
     * The File path.
     */
    String FilePath;

    /**
     * The Phone model.
     */
    String PhoneModel;

    /**
     * The Android version.
     */
    String AndroidVersion;

    /**
     * The Board.
     */
    String Board;

    /**
     * The Brand.
     */
    String Brand;

    /**
     * The Device.
     */
    String Device;

    /**
     * The Display.
     */
    String Display;

    /**
     * The Finger print.
     */
    String FingerPrint;

    /**
     * The Host.
     */
    String Host;

    /**
     * The id.
     */
    String ID;

    /**
     * The Manufacturer.
     */
    String Manufacturer;

    /**
     * The Model.
     */
    String Model;

    /**
     * The Product.
     */
    String Product;

    /**
     * The Tags.
     */
    String Tags;

    /**
     * The Time.
     */
    long Time;

    /**
     * The Type.
     */
    String Type;

    /**
     * The User.
     */
    String User;

    /**
     * The Custom parameters.
     */
    HashMap<String, String> CustomParameters = new HashMap<String, String>();

    /**
     * The Previous handler.
     */
    private Thread.UncaughtExceptionHandler PreviousHandler;

    /**
     * The S_m instance.
     */
    private static ErrorReporter S_mInstance;

    /**
     * The Cur context.
     */
    private Context CurContext;

    /**
     * Adds the custom data.
     *
     * @param Key   the key
     * @param Value the value
     */
    public void AddCustomData(String Key, String Value) {
        CustomParameters.put(Key, Value);
    }

    /**
     * Creates the custom info string.
     *
     * @return the string
     */
    private String CreateCustomInfoString() {
        String CustomInfo = "";
        Iterator<String> iterator = CustomParameters.keySet().iterator();
        while (iterator.hasNext()) {
            String CurrentKey = (String) iterator.next();
            String CurrentVal = CustomParameters.get(CurrentKey);
            CustomInfo += CurrentKey + " = " + CurrentVal + "\n";
        }
        return CustomInfo;
    }

    /**
     * Gets the single instance of ErrorReporter.
     *
     * @return single instance of ErrorReporter
     */
    static ErrorReporter getInstance() {
        if (S_mInstance == null)
            S_mInstance = new ErrorReporter();
        return S_mInstance;
    }

    /**
     * Inits the.
     *
     * @param context the context
     */
    public void Init(Context context) {
        PreviousHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        CurContext = context;
    }

    /**
     * Gets the available internal memory size.
     *
     * @return the available internal memory size
     */
    public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * Gets the total internal memory size.
     *
     * @return the total internal memory size
     */
    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * Recolt informations.
     *
     * @param context the context
     */
    void RecoltInformations(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi;
            // Version
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            VersionName = pi.versionName;
            buildNumber = currentVersionNumber(context);
            // Package name
            PackageName = pi.packageName;

            // Device model
            PhoneModel = android.os.Build.MODEL;
            // Android version
            AndroidVersion = android.os.Build.VERSION.RELEASE;

            Board = android.os.Build.BOARD;
            Brand = android.os.Build.BRAND;
            Device = android.os.Build.DEVICE;
            Display = android.os.Build.DISPLAY;
            FingerPrint = android.os.Build.FINGERPRINT;
            Host = android.os.Build.HOST;
            ID = android.os.Build.ID;
            Model = android.os.Build.MODEL;
            Product = android.os.Build.PRODUCT;
            Tags = android.os.Build.TAGS;
            Time = android.os.Build.TIME;
            Type = android.os.Build.TYPE;
            User = android.os.Build.USER;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the information string.
     *
     * @return the string
     */
    public String CreateInformationString() {
        RecoltInformations(CurContext);

        String ReturnVal = "";
        ReturnVal += "Version : " + VersionName;
        ReturnVal += "\n";
        ReturnVal += "Build Number : " + buildNumber;
        ReturnVal += "\n";
        ReturnVal += "Package : " + PackageName;
        ReturnVal += "\n";
        ReturnVal += "FilePath : " + FilePath;
        ReturnVal += "\n";
        ReturnVal += "Phone Model" + PhoneModel;
        ReturnVal += "\n";
        ReturnVal += "Android Version : " + AndroidVersion;
        ReturnVal += "\n";
        ReturnVal += "Board : " + Board;
        ReturnVal += "\n";
        ReturnVal += "Brand : " + Brand;
        ReturnVal += "\n";
        ReturnVal += "Device : " + Device;
        ReturnVal += "\n";
        ReturnVal += "Display : " + Display;
        ReturnVal += "\n";
        ReturnVal += "Finger Print : " + FingerPrint;
        ReturnVal += "\n";
        ReturnVal += "Host : " + Host;
        ReturnVal += "\n";
        ReturnVal += "ID : " + ID;
        ReturnVal += "\n";
        ReturnVal += "Model : " + Model;
        ReturnVal += "\n";
        ReturnVal += "Product : " + Product;
        ReturnVal += "\n";
        ReturnVal += "Tags : " + Tags;
        ReturnVal += "\n";
        ReturnVal += "Time : " + Time;
        ReturnVal += "\n";
        ReturnVal += "Type : " + Type;
        ReturnVal += "\n";
        ReturnVal += "User : " + User;
        ReturnVal += "\n";
        ReturnVal += "Total Internal memory : " + getTotalInternalMemorySize();
        ReturnVal += "\n";
        ReturnVal += "Available Internal memory : " + getAvailableInternalMemorySize();
        ReturnVal += "\n";

        return ReturnVal;
    }

    /*
     * Error Report Generator
     */

    public void uncaughtException(Thread t, Throwable e) {
        String Report = "";
        Date CurDate = new Date();
        Report += "Error Report collected on : " + CurDate.toString();
        Report += "\n";
        Report += "\n";
        Report += "Informations :";
        Report += "\n";
        Report += "==============";
        Report += "\n";
        Report += "\n";
        Report += CreateInformationString();

        Report += "Custom Informations :\n";
        Report += "=====================\n";
        Report += CreateCustomInfoString();

        Report += "\n\n";
        Report += "Stack : \n";
        Report += "======= \n";
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        e.printStackTrace(printWriter);
        String stacktrace = result.toString();
        Report += stacktrace;

        Report += "\n";
        Report += "Cause : \n";
        Report += "======= \n";

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            Report += result.toString();
            cause = cause.getCause();
        }
        printWriter.close();
        Report += "**** End of current Report ***";
        SaveAsFile(Report);
        // SendErrorMail( Report );
        PreviousHandler.uncaughtException(t, e);
    }

    /**
     * Save as file.
     *
     * @param ErrorContent the error content
     */
    private void SaveAsFile(String ErrorContent) {
        try {

            File logFile = new File(Environment.getExternalStorageDirectory(), "/ErrorLogs_futrgram" + ".txt");
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            BufferedWriter output = new BufferedWriter(new FileWriter(logFile));
            output.write(ErrorContent);
            output.close();


        } catch (Exception e) {
            System.out.println("Exception" + e.toString());
        }
    }

    /**
     * Current version number.
     *
     * @param a the a
     * @return the string
     */
    public static String currentVersionNumber(Context a) {
        PackageManager pm = a.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("com.ids.compliance", PackageManager.GET_SIGNATURES);
            return pi.versionName + (pi.versionCode > 0 ? " (" + pi.versionCode + ")" : "");
        } catch (NameNotFoundException e) {
            return null;
        }
    }
}
