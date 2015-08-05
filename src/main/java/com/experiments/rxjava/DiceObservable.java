package com.experiments.rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;

import java.util.Random;

/**
 * Created by thimes on 11/19/14.
 */
public class DiceObservable {
    private static final Random rand = new Random();

    public static void main(String[] args) {
        getDiceObservable(3, 6)
                .scan(sumEmittedItemsFunc)
                .subscribe(printTotalAction);

    }

    private static final Func2<Integer, Integer, Integer> sumEmittedItemsFunc = new Func2<Integer, Integer, Integer>() {
        @Override
        public Integer call(Integer integer, Integer integer2) {
            return integer + integer2;
        }
    };

    private static final Action1<Integer> printTotalAction = new Action1<Integer>() {
        @Override
        public void call(Integer integer) {
            System.out.println("Current total is " + integer);
        }
    };

    private static Observable<Integer> getDiceObservable(final int numDice, final int numSides) {

        Observable<Integer> diceRollingObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                Observable.range(0, numDice).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        int roll = rand.nextInt(numSides) + 1;
//                        System.out.println("rolled " + roll);
                        subscriber.onNext(roll);
                    }
                });
            }
        });

        return diceRollingObservable;
    }
}
