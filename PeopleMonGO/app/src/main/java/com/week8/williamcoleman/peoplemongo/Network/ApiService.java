package com.week8.williamcoleman.peoplemongo.Network;

import com.week8.williamcoleman.peoplemongo.Models.Auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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

}
