package com.artishub.app.model;

/**
 * Created by ashutosh on 7/27/2018.
 */
public class CurrentUser {


    /**
     * error_string : Sign Up successful
     * error_code : 0
     * result : {"user_id":"49","email":"ashut@tes.com","name":"","country_code":"+234","mobile_number":"8745612345","address":"","city":"","business_category_name":"","delivery_preference":"","payment_mode":"","step_status":"1"}
     * otp : 1234
     */

    private String error_string;
    private int error_code;
    private ResultBean result;
    private int otp;

    public String getError_string() {
        return error_string;
    }

    public void setError_string(String error_string) {
        this.error_string = error_string;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public static class ResultBean {
        /**
         * user_id : 49
         * email : ashut@tes.com
         * name :
         * country_code : +234
         * mobile_number : 8745612345
         * address :
         * city :
         * business_category_name :
         * delivery_preference :
         * payment_mode :
         * step_status : 1
         */

        private String user_id;
        private String email;
        private String name;
        private String country_code;
        private String mobile_number;
        private String address;
        private String city;
        private String business_category_name;
        private String delivery_preference;
        private String payment_mode;
        private String step_status;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getMobile_number() {
            return mobile_number;
        }

        public void setMobile_number(String mobile_number) {
            this.mobile_number = mobile_number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBusiness_category_name() {
            return business_category_name;
        }

        public void setBusiness_category_name(String business_category_name) {
            this.business_category_name = business_category_name;
        }

        public String getDelivery_preference() {
            return delivery_preference;
        }

        public void setDelivery_preference(String delivery_preference) {
            this.delivery_preference = delivery_preference;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getStep_status() {
            return step_status;
        }

        public void setStep_status(String step_status) {
            this.step_status = step_status;
        }
    }
}
