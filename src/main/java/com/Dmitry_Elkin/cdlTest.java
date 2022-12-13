package com.Dmitry_Elkin;

import java.util.concurrent.CountDownLatch;

public class cdlTest {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(90);
        System.out.println("start main thread");

        System.out.println("start thread");
        new Thread(new MyThread(latch)).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("finish of main thread");
    }
}

class MyThread implements Runnable {
    CountDownLatch cdl;

    public MyThread(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("current thread is " + Thread.currentThread().getName());
        System.out.println("getCount = " + cdl.getCount());
        for (int i = 0; i < 100; i++) {
            System.out.println("inner thread is working "+i);
            cdl.countDown();
        }
    }
}