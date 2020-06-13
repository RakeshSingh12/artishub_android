package com.artishub.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ashutosh on 8/20/2018.
 */
public class MyAddressModel implements Serializable {


    private String error_string;
    private int error_code;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * address_id : 4
         * address : A-26, Sector 63 Rd, A Block, Sector 63, Noida, Uttar Pradesh 201301, India
         * landmark : Near Bmw Showroom
         * house_no : 801
         * name : Ashutosh
         * country_code : +91
         * mobile_number : 8743888923
         * user_id : 15
         */

        private String address_id;
        private String address;
        private String landmark;
        private String house_no;
        private String name;
        private String country_code;
        private String mobile_number;
        private String user_id;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getHouse_no() {
            return house_no;
        }

        public void setHouse_no(String house_no) {
            this.house_no = house_no;
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
