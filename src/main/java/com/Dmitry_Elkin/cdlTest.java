package com.Dmitry_Elkin;

import java.util.concurrent.CountDownLatch;


/**
 * simple test of CountDownLatch ;)
 */

public class cdlTest {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(20);
        System.out.println("start main thread");

        new Thread(new MyThread(latch)).start();
        new Thread(new MyThread2(latch)).start();

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
            System.out.println("работает служебный поток "+i);
            cdl.countDown();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class MyThread2 implements Runnable {
    CountDownLatch cdl;

    public MyThread2(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("current thread is " + Thread.currentThread().getName());
//        System.out.println("getCount = " + cdl.getCount());
        for (int i = 0; i < 100; i++) {
            System.out.println(" работает целевой поток "+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
