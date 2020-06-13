package com.artishub.app.constant;

/**
 * Created by vivek on 72/10/17.
 */

public interface ServerConstants {
    //******** for Development **********//
    public static String HOST = "http://52.27.53.102:8001/";
    public static String BASE_URL = "http://52.27.53.102/artisthub/api/v1/user/";
    public static String IMAGE_BASE_URL = "http://52.27.53.102:8001/public/";
    public static String LOGIN="login";
    public static String SIGN_UP = "signup";
    public static String RESEND_OTP = "resendOTP";
    public static String VERIFY_OTP="otpVerification";
    public static String FORGOT_PASSWORD="forgotpassword";
    public static String GIFT_LIST="getGifts";
    public static String SEARCH_GIFT_LIST="searchGift";
    public static String QR_CODE="scanCode";
    public static String ADD_INVOICE="addInvoice";
    public static String GET_USER_SCANNED="getUserScanHistory";
    public static  String UPDATE_DTCODE="updateDTCode";

    public static String UPDATE_INFO = "user/updateInfo";
    public static String UPDATE_DP = "user/uploadDP";
    public static String SYNC_CONTACT = "user/syncContact";
    public static String SYNC_CONTACT_NEW="user/syncContact12";
    public static String CAPSULE_CREATE = "capsule/create";
    public static String CAPSULE_INBOX = "capsule/inbox";
    public static String CAPSULE_EMOJI = "capsule/emojis";
    public static String LAST_ACCESS = "user/lastAccess";
    public static String CAPSULE_OUTBOX = "capsule/outbox";
    public static String CAPSULE_UNLOCK = "capsule/unlock";
    public static String CHECK_ANSWER = "capsule/checkAnswer";
    public static String CHECKUSERNAME= "user/checkusername";
    public static String GET_All_NOTIFICATION="capsule/getAllNotificationByRid";

    public static String FORGOT_PASS = "forgotPassword";
    public static String CHECK_SOCIAL_USER = "socialSignIn";
    public static String GET_CATEGORY_LIST = "getCategoryList";
    public static String GET_PACKAGE_LIST = "getPackageList";
    public static String UPDATE_LOCATION = "updateLocation";
    public static String UPDATE_DEVICE_TOKEN = "updateToken";
    public static String UPDATE_PROFILE = "updateProfile";
    public static String CONTACT_US = "contactUs";
    public static String RATE_SHEDULE = "rateToSchedule";
    public static String UPDATE_DEVICE = "updateDevice";
    public static String ON_OFF_NOTIFICATION = "manageNotificationFlag";
    public static String APPLY_COUPON = "checkCouponValidOrNot";
    public static String SCHEDULE_BOOKING = "createWashSchedule";
    public static String SCHEDULE_HISTORY = "washScheduleHistory";
    public static String HISTORY_DETAIL = "washScheduleHistoryDetails";

    //************Privacy and terms & conditions****************//
    public static String TERMS_OF_SERVICES = "https://www.google.com/";
    public static String PRIVACY_POLICY = "https://www.google.com/";


}
