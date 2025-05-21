package com.twisha.database;

public interface KeyValueStore<K, V> {

    V getValue(K key);

    void setValue(K key, V value);
}
