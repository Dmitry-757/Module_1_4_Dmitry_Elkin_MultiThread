package com.Dmitry_Elkin.method2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Foo foo = new Foo();

        CompletableFuture.runAsync(() -> {
            foo.third(new Thread());
        });

        CompletableFuture.runAsync(() -> {
            foo.first(new Thread());
        });

        CompletableFuture.runAsync(() -> {
            foo.second(new Thread());
        }).get();



    }
}

class Foo {
    private CountDownLatch latch1;
    private CountDownLatch latch2;
    private CountDownLatch latch3;

    public Foo() {

        this.latch1 = new CountDownLatch(0);
        this.latch2 = new CountDownLatch(1);
        this.latch3 = new CountDownLatch(1);
    }


    public void first(Runnable r) {
        System.out.println("latch1.getCount() " + latch1.getCount());
        try {
            latch1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("First was called by " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch2.countDown();
        latch1 = new CountDownLatch(1);
    }

    public void second(Runnable r) {
        System.out.println("latch2.getCount() " + latch2.getCount());
        try {
            latch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Second was called by " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch3.countDown();
        latch2 = new CountDownLatch(1);
//        }
    }

    public void third(Runnable r) {
        System.out.println("latch3.getCount() " + latch3.getCount());
        try {
            latch3.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Third was called by " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        latch3 = new CountDownLatch(1);
        latch1.countDown();

    }

}