package com.Dmitry_Elkin;

import java.util.concurrent.CountDownLatch;

public class Foo {
    CountDownLatch cdlB;
    CountDownLatch cdlC;
    private final Object lock1;
    private final Object lock2;
    private final Object lock3;





    public Foo(CountDownLatch cdlB, CountDownLatch cdlC) {
        this.cdlB = cdlB;
        this.cdlC = cdlC;
        this.lock1 = new Object();
        this.lock2 = new Object();
        this.lock3 = new Object();
    }

    public void first (Runnable r){
        synchronized(lock1) {
            System.out.println("First was called by " + Thread.currentThread().getName());

            cdlB.countDown();
            cdlC.countDown();
        }
    }
    public  void second (Runnable r){
        synchronized(lock2) {
            try {
                cdlB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Second was called by " + Thread.currentThread().getName());

            cdlC.countDown();
            cdlB = new CountDownLatch(1);
        }
    }
    public  void third (Runnable r){
        synchronized(lock3) {
            try {
                cdlC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Third was called by " + Thread.currentThread().getName());
            cdlC = new CountDownLatch(2);
        }
    }

}
