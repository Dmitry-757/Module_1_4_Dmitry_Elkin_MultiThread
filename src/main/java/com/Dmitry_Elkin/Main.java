package com.Dmitry_Elkin;

public class Main {
    public static void main(String[] args) {
        final int THREADS_COUNT = 3;
        Foo foo = new Foo();

        System.out.println("Let`s start! ;)");

        Thread t1 =
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
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
            for (int i = 0; i < 10000; i++) {
                foo.second(Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.setName("B");

        Thread t3 =
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                foo.third(Thread.currentThread());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t3.setName("C");

        t1.start();
        t2.start();
        t3.start();
    }
}