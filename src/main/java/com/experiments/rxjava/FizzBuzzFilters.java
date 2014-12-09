package com.experiments.rxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;

/**
 * Created by thimes on 11/19/14.
 */
public class FizzBuzzFilters {
    public static void main(String[] args) {
        new FizzBuzzFilters().fizzbuzz();
    }

    public void fizzbuzz() {

        ConnectableObservable<Integer> fizzbuzzObserver = Observable.range(1,100).publish();

        fizzbuzzObserver.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 3 == 0 && integer % 5 != 0;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer + " : Fizz");
            }
        });

        fizzbuzzObserver.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 3 != 0 && integer % 5 == 0;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer + " : Buzz");
            }
        });
        fizzbuzzObserver.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 3 == 0 && integer % 5 == 0;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer + " : Buzz");
            }
        });

        fizzbuzzObserver.connect();
    }
}
