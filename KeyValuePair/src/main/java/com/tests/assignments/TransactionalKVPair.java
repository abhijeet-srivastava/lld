package com.tests.assignments;

public interface TransactionalKVPair {

    int beginTransaction();

    void commit(int txnId);

    void rollBack(int txnId);
}
