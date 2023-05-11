package com.tests.assignments;

import com.tests.assignments.exception.KeyNotFoundException;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class KeyValuePair<K, V> implements IKeyValuePair<K, V>, Transactional{

    private TransactionContext  context;

    private Deque<TransactionContext> stack;

    public class TransactionContext implements IKeyValuePair<K,V> {
        private Map<K, V>  store;

        public TransactionContext(Map<K, V> store) {
            this.store = new HashMap<>(store);
        }
        @Override
        public V get(K key) {
            return this.store.get(key);
        }

        @Override
        public void put(K key, V value) {
            this.store.put(key, value);
        }

        @Override
        public void delete(K key) {
            this.store.remove(key);
        }
    }

    public void beginTransaction() {
        if(this.stack == null) {
            this.stack = new ArrayDeque<>();
        }
        TransactionContext current = new TransactionContext(this.context.store);
        this.stack.push(this.context);
        this.context = current;
    }

    @Override
    public void commit() {
        this.stack.pop();
    }

    @Override
    public void rollBack() {
        TransactionContext parent = this.stack.pop();
        this.context = parent;
    }

    public KeyValuePair() {
        this.context = new TransactionContext(new HashMap<K, V>());
    }

    @Override
    public V get(K key) {
        return this.context.get(key);
    }

    @Override
    public void put(K key, V value) {
        this.context.put(key, value);
    }

    @Override
    public void delete(K key) {
        this.context.delete(key);

    }
}
