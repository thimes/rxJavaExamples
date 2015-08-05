package com.experiments.rxjava.retrofit;

import com.experiments.rxjava.retrofit.data.Api5Message;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by thimes on 12/9/14.
 */
public interface Api5Service {
    @GET("/series/{id}/related/?cs=YT8B4uuM6NrHXuVf")
    Observable<Api5Message> related(@Path("id") int id);
}
