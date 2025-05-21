package com.twisha.database;

public class InMemoryDatabase<K, V> implements KeyValueStore<K, V>{
    @Override
    public V getValue(K key) {
        return null;
    }

    @Override
    public void setValue(K key, V value) {

    }
}
