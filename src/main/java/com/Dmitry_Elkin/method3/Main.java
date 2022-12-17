package com.Dmitry_Elkin.method3;


import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Foo foo = new Foo();

        System.out.println("2");
        CompletableFuture.runAsync(() -> {
            System.out.println("2 " + Thread.currentThread().getName());
            foo.second(new Thread());
        });

        System.out.println("1");
        CompletableFuture.runAsync(() -> {
            System.out.println("1 " + Thread.currentThread().getName());
            foo.first(new Thread());
        });

        System.out.println("3");
        CompletableFuture.runAsync(() -> {
            System.out.println("3 " + Thread.currentThread().getName());
            foo.third(new Thread());
        });

        Thread.sleep(5000);

//        for (int i = 0; i < 20; i++) {
//
//            int j = new Random().nextInt(1, 4);
//            switch (j) {
//                case 1 -> {
//                    System.out.println("1");
//                    CompletableFuture.runAsync(() -> {
//                        System.out.println("1 " + Thread.currentThread().getName());
//                        foo.first(new Thread());
//                    });
//                }
//                case 2 -> {
//                    System.out.println("2");
//                    CompletableFuture.runAsync(() -> {
//                        System.out.println("2 " + Thread.currentThread().getName());
//                        foo.second(new Thread());
//                    });
//                }
//                case 3 -> {
//                    System.out.println("3");
//                    CompletableFuture.runAsync(() -> {
//                        System.out.println("3 " + Thread.currentThread().getName());
//                        foo.third(new Thread());
//                    });
//                }
//            }
//
//            Thread.sleep(3500);
//        }
    }
}


class Foo {
    private final Lock lock1;
    private final Condition condition1;
    private final Lock lock2;
    private final Condition condition2;
    private final Lock lock3;
    private final Condition condition3;

    public Foo() {
        lock1 = new ReentrantLock();
        lock2 = new ReentrantLock();
        lock3 = new ReentrantLock();
        condition1 = lock1.newCondition();
        condition2 = lock2.newCondition();
        condition3 = lock3.newCondition();
    }

    public void first(Runnable r) {
//        if (lock1.tryLock()) {
            System.out.println("lock1.tryLock() = true");
            try {
                lock1.lock();
//                condition1.await();
                System.out.println("First was called by " + Thread.currentThread().getName());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            } finally {
                condition2.signal();
                lock1.unlock();
            }
            System.out.println("1 lock2.tryLock() = "+lock2.tryLock());
//        }

    }

    public void second(Runnable r) {
//        if (lock2.tryLock()) {
            System.out.println("lock2.tryLock() = true");
            try {
                lock2.lock();
                System.out.println("waiting first");
//                condition2.await();
                System.out.println("Second was called by " + Thread.currentThread().getName());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            } finally {
                condition3.signal();
                lock2.unlock();
            }
            System.out.println("2 lock3.tryLock() = "+lock3.tryLock());
        }
//    }

    public void third(Runnable r) {
//        if (lock3.tryLock()) {
            System.out.println("lock3.tryLock() = true");
            try {
                lock3.lock();
//                condition3.await();
                System.out.println("Third was called by " + Thread.currentThread().getName());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
            } finally {
                condition1.signal();
                lock3.unlock();
            }
            System.out.println("3 lock1.tryLock() = "+lock1.tryLock());
//        }
    }
}
