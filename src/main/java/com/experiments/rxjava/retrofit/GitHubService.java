package com.experiments.rxjava.retrofit;

import com.experiments.rxjava.retrofit.data.Contributor;
import com.experiments.rxjava.retrofit.data.User;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

import java.util.List;

/**
 * Created by thimes on 12/9/14.
 */
public interface GitHubService {
    @GET("/repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo
    );

    @GET("/repos/{owner}/{repo}/contributors")
    void contributorsAsync(
            @Path("owner") String owner,
            @Path("repo") String repo,
            Callback<List<Contributor>> contributors
    );

    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> getContributorsObservable(
            @Path("owner") String owner,
            @Path("repo") String repo
    );

    @GET("/users/{user}")
    Observable<User> getContributorsObservable(
            @Path("user") String user
    );
}
