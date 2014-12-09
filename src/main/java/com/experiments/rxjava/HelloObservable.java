package com.experiments.rxjava;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by thimes on 11/19/14.
 */
public class HelloObservable {
    public static void main(String[] args) {
        new Runnable() {

            @Override
            public void run() {
                new HelloObservable().sayHello();
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
}
