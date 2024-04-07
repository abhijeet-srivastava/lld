package com.test.assignments.singleton;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonBroker {

    @SneakyThrows
    public static void main(String[] args) {
        SingletonBroker sb = new SingletonBroker();
        RateLimiter.initialise(60, 10);
        sb.tryBreaking();
        sb.tryBreakingThroughReflection();
        sb.enumSingletonRateLimiter();
    }

    private void enumSingletonRateLimiter() throws InstantiationException {
        RateLimiterEnum  rateLimiter = RateLimiterEnum.RATE_LIMITER;
        rateLimiter.initialize(100, 10);
        rateLimiter.showConfig();
        System.out.printf("IsAllowed: %b\n", rateLimiter.isAllowed(100));
    }

    private void tryBreakingThroughReflection() {
        RateLimiter INSTANCE_1 = RateLimiter.getInstance(), INSTANCE_2 = null;
        try {
            Constructor constructor = INSTANCE_1.getClass().getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
            INSTANCE_2 = (RateLimiter) constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Instances are equal: %b\n", INSTANCE_1 == INSTANCE_2);
        System.out.printf("INSTANCE_1 config\n");
        INSTANCE_1.showConfig();
        System.out.printf("INSTANCE_2 config\n");
        INSTANCE_2.showConfig();
    }

    private void tryBreaking() {
        RateLimiter INSTANCE_1 = RateLimiter.getInstance(), INSTANCE_2 = null;

        try(FileOutputStream fos = new FileOutputStream("LazyIntialisedSingletonSerFile.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(INSTANCE_1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(FileInputStream fis = new FileInputStream("LazyIntialisedSingletonSerFile.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            INSTANCE_2 = (RateLimiter)ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Instances are equal: %b\n", INSTANCE_1 == INSTANCE_2);
        System.out.printf("INSTANCE_1 config\n");
        INSTANCE_1.showConfig();
        System.out.printf("INSTANCE_2 config\n");
        INSTANCE_2.showConfig();
    }
}
