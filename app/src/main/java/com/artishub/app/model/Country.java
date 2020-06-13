package com.artishub.app.model;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class Country {
    private String countryCode;
    private String countryName;
    private String dileCode;

    public Country(String countryCode, String countryName, String dileCode) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.dileCode = dileCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getDileCode() {
        return dileCode;
    }
}
