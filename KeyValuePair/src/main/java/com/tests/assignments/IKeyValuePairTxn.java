package com.tests.assignments;

public interface IKeyValuePairTxn<K, V> {

    V get(int txnId, K key);

    void put(int txnId, K key, V value);
}
