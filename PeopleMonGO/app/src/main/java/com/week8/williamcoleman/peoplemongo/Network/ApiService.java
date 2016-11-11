package com.week8.williamcoleman.peoplemongo.Network;

import com.week8.williamcoleman.peoplemongo.Models.Auth;
import com.week8.williamcoleman.peoplemongo.Models.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by williamcoleman on 11/7/16.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("token")
    Call<Auth> login(@Field("grant_type") String grantType,
                     @Field("username") String email,
                     @Field("password") String password);

    @POST("api/Auth/Register")
    Call<Void> register(@Body Auth auth);

    @GET("/v1/User/Nearby")
    Call<Users[]>nearby(@Query("radiusInMeters") Integer radiusInMeters);

    @POST("v1/User/CheckIn")
    Call<Void>checkIn(@Body Auth auth);

    @POST("/api/Account/UserInfo")
    Call<Void> editProfile(@Body Auth auth);

    @GET("/api/Account/UserInfo")
    Call<Auth> viewProfile();

    @POST("/v1/User/Catch")
    Call<Void> catchpMon(@Body Users user);

    @GET("/v1/User/Caught")
    Call<Users[]> caughtpMon(@Body Users user);
}
