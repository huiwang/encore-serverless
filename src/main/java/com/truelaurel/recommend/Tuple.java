package com.truelaurel.recommend;

import java.util.Objects;

public class Tuple<U, T, V> {
    private final U first;
    private final T second;
    private final V third;

    public Tuple(U first, T second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }


    public U getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?, ?> tuple = (Tuple<?, ?, ?>) o;
        return Objects.equals(first, tuple.first) &&
                Objects.equals(second, tuple.second) &&
                Objects.equals(third, tuple.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                '}';
    }
}
