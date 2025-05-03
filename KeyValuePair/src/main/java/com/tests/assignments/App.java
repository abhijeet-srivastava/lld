package com.tests.assignments;

//import com.tests.assignments.tst.KVStoreSingleThread;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        App app = new App();
        app.testTxnKeyValuePair();
    }

    private void testTxnKeyValuePair() {
        KVStoreSingleThread<String, Integer> kvStore = new KVStoreSingleThread<>(KVStoreSingleThread.IsolationLevel.REPEATABLE_READ);
        int t1 = kvStore.beginTransaction();
        kvStore.put(t1, "a", 1);
        kvStore.put(t1, "b", 2);
        kvStore.commit(t1);
        // t1 puts some initial key-value pairs into the store
        int t2 = kvStore.beginTransaction();
        kvStore.get(t2, "a");
        int t3 = kvStore.beginTransaction();
        kvStore.put(t3, "a", 2);
        kvStore.commit(t3);
        kvStore.get(t2, "a");
        kvStore.get(t2, "c");
        int t4 = kvStore.beginTransaction();
        kvStore.put(t4, "c", 1);
        kvStore.put(t4, "b", 3);
        int t5 = kvStore.beginTransaction();
        kvStore.put(t5, "a", 3);
        kvStore.get(t2, "a");
        kvStore.put(t5, "b", 4);
        kvStore.commit(t5);
        kvStore.commit(t4);
        kvStore.commit(t2);
        kvStore.printStore();
    }
}
