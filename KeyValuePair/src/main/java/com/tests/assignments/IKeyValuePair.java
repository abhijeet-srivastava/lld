package com.tests.assignments;

public interface IKeyValuePair<K, V> {
    V get(K key);

    void put(K key, V value);

    void delete(K key);
}
