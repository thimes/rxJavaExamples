package com.experiments.rxjava;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by thimes on 11/19/14.
 */
public class MapExample {

    private static final Func1<String, Integer> parseStringToInt =
            new Func1<String, Integer>() {
                @Override
                public Integer call(String emittedString) {
                    return Integer.parseInt(emittedString);
                }
            };

    private static final Func1<String, Boolean> canParseAsInt =
            new Func1<String, Boolean>() {
                @Override
                public Boolean call(String s) {
                    try {
                        Integer.parseInt(s);
                    } catch (Exception e) {
                        return false;
                    }
                    return true;
                }
            };

    public static void main(String[] args) {
        Observable
                .from(new String[]{"1", "blah"})
                .filter(canParseAsInt)
                .map(parseStringToInt)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer ints) {
                        System.out.println(ints);
                    }
                });
    }

}
