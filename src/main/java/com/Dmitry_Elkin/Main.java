package com.Dmitry_Elkin;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        CountDownLatch latch2 = new CountDownLatch(1);
        CountDownLatch latch3 = new CountDownLatch(2);

        Foo foo = new Foo(latch2, latch3);

        System.out.println("Let`s start! ;)");

        Thread t1 =
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                foo.first(Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.setName("A");

        Thread t2 =
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                foo.second(Thread.currentThread());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.setName("B");

        Thread t3 =
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                foo.third(Thread.currentThread());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t3.setName("C");

        t1.start();
        System.out.println("t1 started");
        t2.start();
        System.out.println("t2 started");
        t3.start();
        System.out.println("t3 started");
    }
}





class MyThread3 implements Runnable{
    Foo foo;
    CountDownLatch cdl;

    public MyThread3(Foo foo, CountDownLatch cdl) {
        this.foo = foo;
        this.cdl = cdl;
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            foo.third(Thread.currentThread());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}