package com.week8.williamcoleman.peoplemongo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by williamcoleman on 11/9/16.
 */

public class Users {

    @SerializedName("avatarBase64")
    private String avatarbase64;

    @SerializedName("UserName")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @SerializedName("fullname")
    private String fullname;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("ApiKey")
    private String apiKey;

    @SerializedName("radiusInMeters")
    private Integer radiusInMeters;

    @SerializedName("CaughtUserId")
    private String monID;

    @SerializedName("UserId")
    private String usersID;

    @SerializedName("Created")
    private Date createdDate;

    @SerializedName("Longitude")
    private Double longitude;

    @SerializedName("Latitude")
    private Double latitude;

    public Users() {
    }

    public Users(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Users(String monID, Integer radiusInMeters) {
        this.monID = monID;
        this.radiusInMeters = radiusInMeters;
    }

    public Users(String email, Double longitude, Double latitude, Date createdDate, String fullname, String avatarbase64) {
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createdDate = createdDate;
        this.fullname = fullname;
        this.avatarbase64 = avatarbase64;
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

    public Integer getRadiusInMeters() {
        return radiusInMeters;
    }

    public void setRadiusInMeters(Integer radiusInMeters) {
        this.radiusInMeters = radiusInMeters;
    }

    public String getMonID() {
        return monID;
    }

    public void setMonID(String monID) {
        this.monID = monID;
    }

    public String getUsersID() {
        return usersID;
    }

    public void setUsersID(String usersID) {
        this.usersID = usersID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
