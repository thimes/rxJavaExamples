package com.experiments.rxjava;

import java.util.Random;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * Created by thimes on 11/19/14.
 */
public class DiceObservable {
    private final int numDice;
    private final int numSides;
    private final Random rand = new Random();

    public static void main(String[] args) {
        new DiceObservable(3, 6).rollAndSum()
        ;
    }

    public DiceObservable(int numDice, int numSides) {
        this.numDice = numDice;
        this.numSides = numSides;
    }

    private Observable<Integer> getDiceObservable() {

        Observable<Integer> diceRollingObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                Observable.range(0, 10).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        int roll = rand.nextInt(numSides) + 1;
                        System.out.println("Rolled " + roll);
                        subscriber.onNext(roll);
                    }
                });
            }
        }).take(numDice);

        return diceRollingObservable;
    }

    private void rollAndSum() {
        Observable<Integer> diceRollingObservable = getDiceObservable();

        Observable<Integer> sumObservable = diceRollingObservable.scan(new Func2<Integer, Integer, Integer>() {
                                                                           @Override
                                                                           public Integer call(Integer integer, Integer integer2) {
                                                                               return integer + integer2;
                                                                           }
                                                                       }
        );

        sumObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Current total is " + integer);
            }
        });

    }

}
