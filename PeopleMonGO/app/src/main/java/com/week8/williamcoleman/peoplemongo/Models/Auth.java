package com.week8.williamcoleman.peoplemongo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by williamcoleman on 11/7/16.
 */

public class Auth {
    @SerializedName("avatarBase64")
    private String avatarbase64;

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("ApiKey")
    private String apiKey;

    @SerializedName("grantType")
    private String grantType;

    @SerializedName("Latitude")
    private Double latitude;

    @SerializedName("Longitude")
    private Double longitude;

    @SerializedName("LastCheckInLatitude")
    private Double lastCheckLat;

    @SerializedName("LastCheckInLongitude")
    private Double lastCheckLon;

    @SerializedName("access_token")
    private String access_Token;

    @SerializedName(".expires")
    private Date tokenExpiration;

    public Auth() {
    }

    public Auth(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Auth(String fullname, String avatarbase64) {
        this.fullname = fullname;
        this.avatarbase64 = avatarbase64;
    }

    public Auth(String email, String fullname, String avatarbase64, String api, String password) {
        this.email = email;
        this.fullname = fullname;
        this.avatarbase64 = avatarbase64;
        this.apiKey = api;
        this.password = password;
    }

    public Auth(String grantType, String email, String password) {
        this.grantType = grantType;
        this.email = email;
        this.password = password;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLastCheckLat() {
        return lastCheckLat;
    }

    public void setLastCheckLat(Double lastCheckLat) {
        this.lastCheckLat = lastCheckLat;
    }

    public Double getLastCheckLon() {
        return lastCheckLon;
    }

    public void setLastCheckLon(Double lastCheckLon) {
        this.lastCheckLon = lastCheckLon;
    }

    public String getAvatarbase64() {
        return avatarbase64;
    }

    public void setAvatarbase64(String avatarbase64) {
        this.avatarbase64 = avatarbase64;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAccess_Token() {
        return access_Token;
    }

    public void setAccess_Token(String access_Token) {
        this.access_Token = access_Token;
    }

    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Date tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
