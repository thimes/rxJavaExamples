package com.experiments.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by thimes on 11/19/14.
 */
public class FlatmapExample {

    private final Random rand = new Random();
    private final int numDigits = 5;
    private final int numObservables = 5;

    public static void main(String[] args) {
        new FlatmapExample().go();
    }

    private void go() {
//        Observable<List<Integer>> numberObservable = createNumberObservable();
        Observable<List<Integer>> numberObservable = Observable.empty();
        for (int i = 0; i < numObservables; i++) {
            numberObservable = numberObservable.concatWith(createRandomNumberObservable());
        }

        numberObservable
                /*
                .flatMap(new Func1<List<Integer>, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(List<Integer> integers) {
                return Observable.from(integers);
            }
        })
                */
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object object) {
                        System.out.println(object.toString());
                    }
                });
    }

    private Observable<List<Integer>> createNumberObservable() {
        return Observable.create(new Observable.OnSubscribe<List<Integer>>() {
            @Override
            public void call(Subscriber<? super List<Integer>> subscriber) {
                List<Integer> numbers = new ArrayList<Integer>();
                for (int i = 0; i < numDigits; i++) {
                    numbers.add(i); // ordered
                }
                subscriber.onNext(numbers);
                subscriber.onCompleted();
            }
        });
    }

    private Observable<List<Integer>> createRandomNumberObservable() {
       return Observable.create(new Observable.OnSubscribe<List<Integer>>() {
            @Override
            public void call(Subscriber<? super List<Integer>> subscriber) {
                List<Integer> numbers = new ArrayList<Integer>();
                for (int i = 0; i < numDigits; i++) {
                    numbers.add(rand.nextInt(10) - 5); // ordered
                }
                subscriber.onNext(numbers);
                subscriber.onCompleted();
            }
        });
    }

}
