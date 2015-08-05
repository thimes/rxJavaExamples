package com.experiments.rxjava;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by thimes on 11/19/14.
 */
public class HelloObservable {
    public static void main(String[] args) {
        HelloObservable.sayHello();
    }

    public static void sayHello() {
        Observable<String> o = Observable.from(new String[]{"Alice", "Bob", "Charlie"});

        Action1 action = new Action1<String>() {
            public void call(String s) {
                System.out.println("Hello " + s + "!");
            }
        };

        o.subscribe(action);
    }

}
