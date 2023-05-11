package com.tests.assignments;

public interface Transactional {
    void beginTransaction();

    void commit();

    void rollBack();
}
