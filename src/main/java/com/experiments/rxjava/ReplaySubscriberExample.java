package com.experiments.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

import java.util.concurrent.TimeUnit;

/**
 * Created by thimes on 11/19/14.
 */
public class ReplaySubscriberExample {

    private static final String[] directions = {
            "N",
            "E",
            "N",
            "E",
            "E",
            "S",
            "S",
            "S",
    };

    public static void main(String[] args) {
        replaySubject.asObservable().subscribe(getUser("Bob"));

        addUserDelayedBy("Carl", 4500);
        addUserDelayedBy("Joe", 6000);
        addUserDelayedBy("Dan", 10000);

        for (int i = 0; i < directions.length; i++) {
            replaySubject.onNext(directions[i]);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        replaySubject.onCompleted();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void addUserDelayedBy(final String user, int delayMillis) {
        Observable
                .just("")
                .delay(delayMillis, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        replaySubject.subscribe(getUser(user));
                    }
                });
    }

    private static Subscriber<String> getUser(final String name) {

        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("User " + name + " eaten by a balrog");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("User " + name + " encountered an error");
            }

            @Override
            public void onNext(String s) {
                System.out.println("User " + name + " moved " + s);
            }
        };
    }

    public static final ReplaySubject<String> replaySubject = ReplaySubject.create();

}
