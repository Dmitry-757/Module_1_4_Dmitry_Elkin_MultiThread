package com.Dmitry_Elkin.method4;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Main {
    private CountDownLatch countDownLatchC = new CountDownLatch(2);
    private CountDownLatch countDownLatchB = new CountDownLatch(1);
    private CyclicBarrier barrier;
    private final Object monitor = new Object();

    public static void main(String[] args) {
        new Main().test();
    }

    public void test() {
        Foo foo = new Foo();

//        for (int i = 0; i < 3; i++) {
//            barrier = new CyclicBarrier(3, new Runnable() {
//                @Override
//                public void run() {
//                    synchronized (monitor) {
//                        countDownLatchC = new CountDownLatch(2);
//                        countDownLatchB = new CountDownLatch(1);
//                        monitor.notify();
//                    }
//                }
//
//            });
//            CompletableFuture.runAsync(() -> {
//                System.out.println("1 " + Thread.currentThread().getName());
//                foo.first();
//            });
//            CompletableFuture.runAsync(() -> {
//                System.out.println("2 " + Thread.currentThread().getName());
//                foo.second();
//            });
//            CompletableFuture.runAsync(() -> {
//                System.out.println("3 " + Thread.currentThread().getName());
//                foo.third();
//            });
//
//            synchronized (monitor) {
//                try {
//                    monitor.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        for (int i = 0; i < 20; i++) {
            barrier = new CyclicBarrier(3, new Runnable() {
                @Override
                public void run() {
                    synchronized (monitor) {
                        countDownLatchC = new CountDownLatch(2);
                        countDownLatchB = new CountDownLatch(1);
                        monitor.notify();
                    }
                }

            });

            int j = new Random().nextInt(1, 4);
            switch (j) {
                case 1 -> {
//                    System.out.println("1");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("1 "+Thread.currentThread().getName());
                        foo.first();
                    });
                }
                case 2 -> {
//                    System.out.println("2");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("2 "+Thread.currentThread().getName());
                        foo.second();
                    });
                }
                case 3 -> {
//                    System.out.println("3");
                    CompletableFuture.runAsync(() -> {
                        System.out.println("3 "+Thread.currentThread().getName());
                        foo.third();
                    });
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    class Foo {
        public void first() {
            System.out.println("A");
            countDownLatchB.countDown();
            countDownLatchC.countDown();
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        public void second() {
            try {
                countDownLatchB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
            countDownLatchC.countDown();
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public void third() {
            try {
                countDownLatchC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C");
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }
}
