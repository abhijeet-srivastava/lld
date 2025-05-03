package com.test.assignments;


import java.util.function.Predicate;

// Reimplement Optional class, without checking the API,
// implementing ofNullable, filter , map and flatMap methods
public class Optional<T> {

    private static final Optional<Object> EMPTY_OPTIONAL = new Optional<>(null);

    private final T value;

    public Optional(T val) {
        this.value = val;
    }

    public T getValue() {
        return this.value;
    }

    public static <T> Optional<T> ofNullable(T value) {
        return new Optional<>(value);
    }

    public  Optional<T> filter(Predicate<T> predicate) {
        return predicate.test(this.value) ? this: (Optional<T>) EMPTY_OPTIONAL;
    }



}
