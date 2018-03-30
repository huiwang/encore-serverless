package com.truelaurel.recommend;

import java.util.Objects;

public class Pair<U, T> {
    private U first;
    private T second;

    public static <U, T> Pair of(U first, T second) {
        return new Pair<>(first, second);
    }

    private Pair(U first, T second) {
        this.first = first;
        this.second = second;
    }

    public U getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
