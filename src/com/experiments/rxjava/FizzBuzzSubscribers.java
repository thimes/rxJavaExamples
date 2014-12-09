package com.experiments.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by thimes on 11/19/14.
 */
public class FizzBuzzSubscribers {
    public static void main(String[] args) {
        new Runnable() {

            @Override
            public void run() {
                new FizzBuzzSubscribers().fizzbuzz();
            }
        }.run();
    }

    public void sayHello() {
        Observable<String> o = Observable.from(new String[]{"Alice", "Bob", "Charlie"});
        o.subscribe(new Action1<String>() {

            @Override
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }

        });
    }

    public void fizzbuzz() {

        // /3 == 0
        Action1<Integer> fizzAction = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                if (o % 3 == 0 && o % 5 != 0) System.out.println(o + ":" + "fizz");
            }
        };
        // /5 == 0
        Action1<Integer> buzzAction = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                if (o % 3 != 0 && o % 5 == 0) System.out.println(o + ":" + "buzz");
            }
        };
        // /5 == 0 && /3 == 0
        Action1<Integer> fizzBuzzAction = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                if (o % 3 == 0 && o % 5 == 0) System.out.println(o + ":" + "fizzbuzz");
            }
        };
        Action1<Integer> allAction = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                if (o % 3 != 0 && o % 5 != 0) System.out.println(o.toString());
            }
        };

        Observable<Integer> fizbuzzObserver = Observable.create(
                new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 1; i <= 100; i++) {
                            subscriber.onNext(i);
                        }
                        subscriber.onCompleted();
                    }
                }).subscribeOn(Schedulers.io());

        ConnectableObservable<Integer> fizzbuzzObserver = fizbuzzObserver.publish();

        fizzbuzzObserver.subscribe(allAction);
        fizzbuzzObserver.subscribe(fizzAction);
        fizzbuzzObserver.subscribe(buzzAction);
        fizzbuzzObserver.subscribe(fizzBuzzAction);

        fizzbuzzObserver.connect();
    }

}
