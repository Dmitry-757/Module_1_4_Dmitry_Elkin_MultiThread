package com.Dmitry_Elkin.method_5_ReentrantLockAndCondition;


import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        Foo foo = new Foo();


//        new Thread(()->{foo.second();}, "B").start();
//        Thread.sleep(1000);
//
//
//
//        new Thread(()->{foo.first();}, "A").start();
//        Thread.sleep(1000);
//
//        new Thread(()->{foo.third();}, "C").start();
//        Thread.sleep(1000);

        boolean starterLaunched = false;
        for (int i = 0; i < 20; i++) {

            int j = new Random().nextInt(1, 4);
            switch (j) {
                case 1 -> {
                    System.out.println("1");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("1 "+Thread.currentThread().getName());
                        foo.first();
                    });
//                    new Thread(()->{foo.first();}, "A").start();
                    Thread.sleep(1000);

                    System.out.println("***************");

                    if (!starterLaunched && foo.getLock1().tryLock()) {
                        foo.getCondition1().signalAll();
                        System.out.println("lock1 is locked from Main");
                        System.out.println(Thread.currentThread());
                        System.out.println("Now it shell call condition1.signal() from Main");
                        System.out.println(Thread.holdsLock(foo.getLock1()));
                        foo.getLock1().unlock();

                        starterLaunched = true;
                    }

                }
                case 2 -> {
                    System.out.println("2");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("2 "+Thread.currentThread().getName());
                        foo.second();
                    });
//                    new Thread(()->{foo.second();}, "B").start();
                    Thread.sleep(1000);
                }
                case 3 -> {
                    System.out.println("3");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("3 "+Thread.currentThread().getName());
                        foo.third();
                    });
//                    new Thread(()->{foo.third();}, "C").start();
                    Thread.sleep(1000);
                }
            }

            Thread.sleep(500);
        }



//        System.out.println("***************");
//
//        if (foo.getLock1().tryLock()) {
//            foo.getCondition1().signalAll();
//            System.out.println("lock1 is locked from Main");
//            System.out.println(Thread.currentThread());
//            System.out.println("Now it shell call condition1.signal() from Main");
//            System.out.println(Thread.holdsLock(foo.getLock1()));
//            foo.getLock1().unlock();
//
//        }

        System.out.println("***************");

        Thread.sleep(5000);
        System.out.println("***************");
        System.out.println("game over ;)");
    }
}


