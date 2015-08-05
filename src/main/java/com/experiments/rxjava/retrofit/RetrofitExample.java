package com.experiments.rxjava.retrofit;

import com.experiments.rxjava.retrofit.data.AccessToken;
import com.experiments.rxjava.retrofit.data.Api5Message;
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
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by thimes on 12/9/14.
 */
public class RetrofitExample {

    boolean finished = false;
    int checks = 0;

    public static void main(String[] args) {

        RetrofitExample example = new RetrofitExample();
    }

    // Client ID
    // d12ae73cc29677c7bdc8
    // Client Secret
    // 32977c0ae5590eddf8e105994a8003e6a6b92d89

    private void checkRelatedContent() {
        Subscriber s =
                new Subscriber<Api5Message>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Done");
                        finished = true;
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("error: " + throwable.getMessage());
                        finished = true;
                    }

                    @Override
                    public void onNext(Api5Message api5Message) {
                        System.out.println(api5Message.message);
                    }
                };

        Observable.range(1, 200).flatMap(new Func1<Integer, Observable<Api5Message>>() {
            @Override
            public Observable<Api5Message> call(Integer integer) {
                return api5Service.related(integer);
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends Api5Message>>() {
            @Override
            public Observable<? extends Api5Message> call(Throwable throwable) {
                return null;
            }
        }).subscribe(s);
    }


    private void tryDavidsQuestion() {
        final Subject<Integer, Integer> s = ReplaySubject.create();

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        s.onCompleted();
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        s.onNext(integer);
                    }
                });

        s.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Just emitted : " + integer);
            }
        });

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ;
        s.window(0, TimeUnit.SECONDS).first().count().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("completed");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("last one: " + integer);
            }
        });

        /*
        new Action1<Observable<Integer>>() {
            @Override
            public void call(Observable<Integer> integerObservable) {
                System.out.println("window was called");
                integerObservable.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("completed");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("error");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("last one: " + integer);
                    }
                });
            }
        });
        */

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private final Api5Service api5Service;

    private final GitHubService service;

    public RetrofitExample() {
//        service = new RestAdapter.Builder()
//                .setEndpoint("https://api.github.com") // The base API endpoint.
//                .build().create(GitHubService.class);
        GitHubLoginService loginService = ServiceGenerator.createService(GitHubLoginService.class, "https://api.github.com");
        AccessToken accessToken = new AccessToken();
        service = ServiceGenerator.createService(GitHubService.class, "https://api.github.com", accessToken);
        api5Service = new RestAdapter.Builder()
                .setEndpoint("http://shudderdev.dramasd.com/api/5")
                .build().create(Api5Service.class);
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
        Observable<Contributor> contributorObservable =
                service.getContributorsObservable("square", "retrofit")
                        .flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
                            @Override
                            public Observable<Contributor> call(List<Contributor> contributors) {
                                return Observable.from(contributors);
                            }
                        });
        contributorObservable
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
                        System.out.println("Contributor: " + user.name);
                    }
                });

                /*
        contributorObservable
                .flatMap(new Func1<Contributor, Observable<List<GithubEvent>>>() {
                    @Override
                    public Observable<List<GithubEvent>> call(Contributor contributor) {
                        return service.getEvents(contributor.login);
                    }
                })
                .subscribe(new Subscriber<List<GithubEvent>>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Completed");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("error: " + throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<GithubEvent> event) {
                        System.out.println("Event: " + event);
                    }
                });
                */


    }
}
