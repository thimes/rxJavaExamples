package com.experiments.rxjava.retrofit;

import com.experiments.rxjava.retrofit.data.Contributor;
import com.experiments.rxjava.retrofit.data.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.List;

/**
 * Created by thimes on 12/9/14.
 */
public class RetrofitExample {

    public static void main(String[] args) {

        RetrofitExample example = new RetrofitExample();
//        example.getSync();
//        example.getAsync();
//        example.getObservable();
        example.getObservableTricky();
    }

    private final GitHubService service;

    public RetrofitExample() {
        service = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com") // The base API endpoint.
                .build().create(GitHubService.class);
    }

    private void getSync() {
        List<Contributor> contributors = service.contributors("thimes", "rxJavaExamples");
        for (Contributor contributor : contributors) {
            System.out.println("sync: " + contributor.login + " - " + contributor.contributions);
        }
        System.out.println("done sync");
    }

    private void getAsync() {
        service.contributorsAsync("thimes", "rxJavaExamples", new Callback<List<Contributor>>() {

            @Override
            public void success(List<Contributor> contributors, Response response) {
                for (Contributor contributor : contributors) {
                    System.out.println("async: " + contributor.login + " - " + contributor.contributions);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
        System.out.println("done sync part of async");
    }

    private void getObservable() {
        service.getContributorsObservable("thimes", "rxJavaExamples").subscribe(new Action1<List<Contributor>>() {
            @Override
            public void call(List<Contributor> contributors) {
                for (Contributor contributor : contributors)
                    System.out.println("Observable: " + contributor.login + " - " + contributor.contributions);
            }
        });
    }

    private void getObservableTricky() {
//        service.getContributorsObservable("thimes", "rxJavaExamples")
        service.getContributorsObservable("square", "retrofit")
                .flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
                    @Override
                    public Observable<Contributor> call(List<Contributor> contributors) {
                        return Observable.from(contributors);
                    }
                })
                .flatMap(new Func1<Contributor, Observable<User>>() {
                    @Override
                    public Observable<User> call(Contributor contributor) {
                        return service.getContributorsObservable(contributor.login);
                    }
                })
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("error: " + throwable.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        System.out.println("Observable: " + user.name );
                    }
                });
    }


}
