package com.Dmitry_Elkin.method_5_ReentrantLockAndCondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Foo {
    private final Lock lock1;
    private final Lock lock2;
    private final Lock lock3;

    private final Condition condition1;
    private final Condition condition2;
    private final Condition condition3;

    public Foo() {
        lock1 = new ReentrantLock();
        lock2 = new ReentrantLock();
        lock3 = new ReentrantLock();

        condition1 = lock1.newCondition();
        condition2 = lock2.newCondition();
        condition3 = lock3.newCondition();
    }

    public Condition getCondition1() {
        return condition1;
    }

    public Condition getCondition2() {
        return condition2;
    }

    public Lock getLock1() {
        return lock1;
    }

    public Lock getLock2() {
        return lock2;
    }

    public void first() {
        if (lock1.tryLock()) {
            System.out.println("lock1 is locked by first");
            try {
                System.out.println("condition1.awaiting");
                condition1.await();
                System.out.println("condition1 was reached.");
                System.out.println("First was called by " + Thread.currentThread().getName());

                if (lock2.tryLock()) {
                    try {
                        System.out.println("lock2 is locked by first");
                        System.out.println("Now it shell call condition2.signal()");
                        condition2.signal();
                    } finally {
                        System.out.println("lock2.unlock()");
                        lock2.unlock();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("lock1.unlock()");
                lock1.unlock();
            }
        }

    }

    public void second() {
        if (lock2.tryLock()) {
            System.out.println("lock2 is locked by second()");
            try {
                System.out.println("condition2.awaiting");
                condition2.await();
                System.out.println("condition2 was reached.");
                System.out.println("Second was called by " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("lock2.unlock()");
                lock2.unlock();
            }


            if (lock3.tryLock()) {
                try {
                    System.out.println("lock3 is locked by second()");
                    System.out.println(Thread.currentThread());
                    System.out.println("Now it shell call condition1.signal()");
                    condition3.signal();
                } finally {
                    System.out.println("lock3.unlock()");
                    lock3.unlock();
                }
            }
        }

    }

    public void third() {
        if (lock3.tryLock()) {
            System.out.println("lock3 is locked by third()");
            try {
                System.out.println("condition3.awaiting");
                condition3.await();
                System.out.println("condition3 was reached.");
                System.out.println("Third was called by " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("lock3.unlock()");
                lock3.unlock();
            }


            if (lock1.tryLock()) {
                try {
                    System.out.println("lock1 is locked by third");
                    System.out.println(Thread.currentThread());
                    System.out.println("Now it shell call condition2.signal()");
                    condition1.signal();
                } finally {
                    System.out.println("lock1.unlock()");
                    lock1.unlock();
                }
            }
        }
    }

}
