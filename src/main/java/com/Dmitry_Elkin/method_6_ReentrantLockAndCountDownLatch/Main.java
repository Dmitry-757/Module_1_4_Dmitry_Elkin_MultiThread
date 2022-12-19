package com.Dmitry_Elkin.method_6_ReentrantLockAndCountDownLatch;


import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        Foo foo = new Foo();

        foo.getCdlA().countDown();
        System.out.println("Now it shell to call condition1.signal() from Main");

        for (int i = 0; i < 10; i++) {

            int j = new Random().nextInt(1, 4);
            switch (j) {
                case 1 -> {
                    System.out.println("1");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("1 " + Thread.currentThread().getName());
                        foo.first();
                    });
                    Thread.sleep(1000);

                    System.out.println("***************");

                }
                case 2 -> {
                    System.out.println("2");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("2 " + Thread.currentThread().getName());
                        foo.second();
                    });
                    Thread.sleep(1000);
                }
                case 3 -> {
                    System.out.println("3");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("3 " + Thread.currentThread().getName());
                        foo.third();
                    });
                    Thread.sleep(1000);
                }
            }

            Thread.sleep(500);
        }


        System.out.println("***************");

        Thread.sleep(5000);
        System.out.println("***************");
        System.out.println("game over ;)");
    }
}


