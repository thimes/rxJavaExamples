package com.experiments.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.Random;

/**
 * Created by thimes on 11/19/14.
 */
public class FlatmapExample {

    private static final Random rand = new Random();

    public static void main(String[] args) {
//        FlatmapExample.flattenMultipleObservables();
        FlatmapExample.spawnNewObservables();
    }

    private static void spawnNewObservables() {
        Observable<Integer> myObservable = Observable.just(0, 1, 2, 3, 4, 5);

        myObservable.flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(final Integer integer) {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        for (int i = 1; i <= 5; i++) {
                            subscriber.onNext((integer * 10) + (i * 2));
                        }
                        subscriber.onNext(-1);
                    }
                });
            }
        })
        .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });
    }

    private static void flattenMultipleObservables() {
        Observable<Integer> evenObservable = Observable.just(2, 4, 6, 8, 10);
        Observable<Integer> oddObservable = Observable.just(1, 3, 5, 7, 9);
        Observable<Integer> negativeObservable = Observable.just(-1, -2, -3, -4, -5);

        Observable<Observable<Integer>> numberObservable = Observable.just(evenObservable, oddObservable, negativeObservable);

        numberObservable
                .flatMap(new Func1<Observable<Integer>, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Observable<Integer> integerObservable) {
                        return integerObservable;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(integer.toString());
                    }
                });
    }

}
