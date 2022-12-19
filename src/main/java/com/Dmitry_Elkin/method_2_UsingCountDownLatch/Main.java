package com.Dmitry_Elkin.method_2_UsingCountDownLatch;

import java.util.Random;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();

//        Executor executor = Executors.newFixedThreadPool(6);
        ExecutorService executor = Executors.newFixedThreadPool(6);

        for (int i = 0; i < 20; i++) {

            int j = new Random().nextInt(1, 4);
            switch (j) {
                case 1 -> {
                    System.out.println("1");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("1 "+Thread.currentThread().getName());
                        foo.first();
                    }, executor);
                }
                case 2 -> {
                    System.out.println("2");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("2 "+Thread.currentThread().getName());
                        foo.second();
                    },executor);
                }
                case 3 -> {
                    System.out.println("3");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("3 "+Thread.currentThread().getName());
                        foo.third();
                    },executor);
                }
            }

            Thread.sleep(500);
        }
        executor.shutdown();
    }
}

class Foo {
    private CountDownLatch latch1;
    private CountDownLatch latch2;
    private CountDownLatch latch3;

    private final Object lock1;
    private final Object lock2;
    private final Object lock3;


    public Foo() {

        this.latch1 = new CountDownLatch(0);
        this.latch2 = new CountDownLatch(1);
        this.latch3 = new CountDownLatch(1);

        this.lock1 = new Object();
        this.lock2 = new Object();
        this.lock3 = new Object();

    }


    public void first() {
        synchronized (lock1) {
            System.out.println("latch1.getCount() " + latch1.getCount());
            try {
                latch1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("First was called by " + Thread.currentThread().getName());

            latch2.countDown();
            latch1 = new CountDownLatch(1);
        }
    }

    public void second() {
        synchronized (lock2) {
            System.out.println("latch2.getCount() " + latch2.getCount());
            try {
                latch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Second was called by " + Thread.currentThread().getName());
            latch3.countDown();
            latch2 = new CountDownLatch(1);
        }
    }


    public void third() {
        synchronized (lock3) {
            System.out.println("latch3.getCount() " + latch3.getCount());
            try {
                latch3.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Third was called by " + Thread.currentThread().getName());
            latch1.countDown();
            latch3 = new CountDownLatch(1);
        }
    }

}