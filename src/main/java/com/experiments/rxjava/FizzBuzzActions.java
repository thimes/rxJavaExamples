package com.experiments.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by thimes on 11/19/14.
 */
public class FizzBuzzActions {
    public static void main(String[] args) {
       new FizzBuzzActions().fizzbuzz();
    }

    public void fizzbuzz() {

        Action1<Integer> labelAction = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                System.out.print("\n" + o.intValue() + ":");
            }
        };

        // /3 == 0
        Action1<Integer> fizzAction = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                if (o % 3 == 0) System.out.print("fizz");
            }
        };
        // /5 == 0
        Action1<Integer> buzzAction = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                if (o % 5 == 0) System.out.print("buzz");
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
                }).subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread());

        ConnectableObservable<Integer> coldObserver = fizbuzzObserver.publish();

        coldObserver.subscribe(labelAction);
        coldObserver.subscribe(fizzAction);
        coldObserver.subscribe(buzzAction);

        coldObserver.connect();
        coldObserver.toBlocking().last();

    }

}
