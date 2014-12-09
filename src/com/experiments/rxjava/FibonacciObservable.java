package com.experiments.rxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thimes on 11/19/14.
 */
public class FibonacciObservable {
    public static void main(String[] args) {
        new Runnable() {

            @Override
            public void run() {
                new FibonacciObservable().go();
            }
        }.run();
    }



    public void go() {

        final Pair<Integer, Integer> fibPair = new Pair<Integer, Integer>(0, 1);

        Observable<Integer> o = Observable.range(0, 100).subscribeOn(Schedulers.io());

        o.take(8).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                int i = fibPair.first + fibPair.second;
                fibPair.first = fibPair.second;

                System.out.println(fibPair.first);

                fibPair.second = i;
            }
        });
    }
}
