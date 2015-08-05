package com.experiments.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by thimes on 11/19/14.
 */
public class SubscriberExample {

    public static void main(String[] args) {
        Observable<String> cleanStringObservable = Observable.from(new String[]{"firsties", "seconds", "3rd", "FORE!"});
        Observable<String> dirtyStringObservable = Observable.from(new String[]{"dirsties", "deconds", null, "DORE!"});

//        cleanStringObservable.subscribe(action);
//        dirtyStringObservable.subscribe(action);

        cleanStringObservable.subscribe(subscriber);
        dirtyStringObservable.subscribe(subscriber);
    }

    private static final Action1<String> action = new Action1<String>() {
        @Override
        public void call(String s) {
            System.out.println("My string " + s + " is " + s.length() + " characters long");
        }
    };

    private static final Action1<Throwable> error = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            System.out.println("Something went awry!");
            throwable.printStackTrace(System.out);
        }
    };

    private static final Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            System.out.println("Finished up cleanly!");
        }

        @Override
        public void onError(Throwable e) {
            error.call(e);
        }

        @Override
        public void onNext(String s) {
            action.call(s);
        }
    };
}

