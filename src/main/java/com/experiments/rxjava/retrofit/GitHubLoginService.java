package com.experiments.rxjava.retrofit;

import com.experiments.rxjava.retrofit.data.AccessToken;
import com.experiments.rxjava.retrofit.data.User;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by thimes on 12/9/14.
 */
public interface GitHubLoginService {


    public static final String BASE_URL = "https://api.github.com";

    @POST("/login")
    User basicLogin();

    @POST("/token")
    AccessToken getAccessToken(@Query("code") String code,
                               @Query("grant_type") String grantType);

}
