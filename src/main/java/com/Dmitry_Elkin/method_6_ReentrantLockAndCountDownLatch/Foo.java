package com.Dmitry_Elkin.method_6_ReentrantLockAndCountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Foo {
    private final Lock lock1;
    private final Lock lock2;
    private final Lock lock3;

    private CountDownLatch cdlA;
    private CountDownLatch cdlB;
    private CountDownLatch cdlC;

    public Foo() {
        lock1 = new ReentrantLock();
        lock2 = new ReentrantLock();
        lock3 = new ReentrantLock();

        cdlA = new CountDownLatch(1);
        cdlB = new CountDownLatch(1);
        cdlC = new CountDownLatch(1);
    }

    public CountDownLatch getCdlA() {
        return cdlA;
    }

    public void first() {
        if (lock1.tryLock()) {
            System.out.println("lock1 is locked by first");
            try {
                System.out.println("condition1.awaiting");
                cdlA.await();
                System.out.println("condition1 was reached.");
                System.out.println("First was called by " + Thread.currentThread().getName());

                    System.out.println("Now it will call the condition2.signal()");
                    cdlB.countDown();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("lock1.unlock()");
                cdlA = new CountDownLatch(1);
                lock1.unlock();
            }
        }

    }

    public void second() {
        if (lock2.tryLock()) {
            System.out.println("lock2 is locked by second()");
            try {
                System.out.println("condition2.awaiting");
                cdlB.await();
                System.out.println("condition2 was reached.");
                System.out.println("Second was called by " + Thread.currentThread().getName());

                        System.out.println("Now it will call the condition3.signal()");
                        cdlC.countDown();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("lock2.unlock()");
                cdlB = new CountDownLatch(1);
                lock2.unlock();
            }


        }

    }

    public void third() {
        if (lock3.tryLock()) {
            System.out.println("lock3 is locked by third()");
            try {
                System.out.println("condition3.awaiting");
                cdlC.await();
                System.out.println("condition3 was reached.");
                System.out.println("Third was called by " + Thread.currentThread().getName());
                        System.out.println("Now it will call the condition1.signal()");
                        cdlA.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("lock3.unlock()");
                cdlC = new CountDownLatch(1);
                lock3.unlock();
            }
        }
    }

}
