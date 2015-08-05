package com.experiments.rxjava;

import com.sun.tools.javac.util.Pair;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by thimes on 11/19/14.
 */
public class FibonacciObservable {
    public static void main(String[] args) {
        FibonacciObservable.go();
    }

    public static void go() {
        final Pair<Integer, Integer>[] fibPair = new Pair[]{new Pair<Integer, Integer>(0, 1)};

        Observable<Integer> o = Observable.range(0, 100).subscribeOn(Schedulers.io());

        o.take(8).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                int i = fibPair[0].fst + fibPair[0].snd;

                System.out.println(fibPair[0].fst);

                fibPair[0] = new Pair<Integer, Integer>(fibPair[0].snd, i);
            }

        });
    }
}
