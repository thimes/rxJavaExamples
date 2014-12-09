package com.experiments.rxjava;

/**
 * Created by thimes on 12/8/14.
 */
public class Pair<T, U> {
    public T first;
    public U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "{"+first+"|"+second+"}";
    }
}
