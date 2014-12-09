package com.experiments.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by thimes on 11/19/14.
 */
public class SubscriberExample {

    public static void main(String[] args) {
        new SubscriberExample().go();
    }

    private void go() {
        Observable<String> cleanStringObservable = Observable.from(new String[]{"firsties", "seconds", "3rd", "FORE!"});
        Observable<String> dirtyStringObservable = Observable.from(new String[]{"firsties", "seconds", null, "FORE!"});

        cleanStringObservable.subscribe(getAction());
        //cleanStringObservable.subscribe(getSubscriber());
        //dirtyStringObservable.subscribe(getAction());
        //dirtyStringObservable.subscribe(getSubscriber());
    }

    private Subscriber<String> getSubscriber() {
        return new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("Finished up cleanly!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Something went awry!");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("My string " + s + " is " + s.length() + " characters long");
                    }
                };
    }

    private Action1<String> getAction() {
        return new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("My string " + s + " is " + s.length() + " characters long");
            }
        };
    }

}
