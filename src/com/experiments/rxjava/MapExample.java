package com.experiments.rxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by thimes on 11/19/14.
 */
public class MapExample {

    public static void main(String[] args) {
        new MapExample().mapOnce();
    }

    private void mapOnce() {

        Observable.from(new String[]{"1", "blah"})
        .map(new Func1<String, String>() {
            @Override
            public String call(String emittedString) {
                return "add something to the front, " + emittedString;
            }
        }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s+" and append something";
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String string) {
                System.out.println(string);
            }
        });
    }

}
