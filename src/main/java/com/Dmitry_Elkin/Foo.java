package com.Dmitry_Elkin;

import java.util.concurrent.CountDownLatch;

public class Foo {

//    CountDownLatch cdlB = new CountDownLatch(1);
    CountDownLatch cdlC = new CountDownLatch(20);

    public synchronized void first (Runnable r){
        System.out.println("First was called by "+Thread.currentThread().getName());
        System.out.println("first()");
//        System.out.println("cdlB = "+cdlB.getCount());
        System.out.println("cdlC = "+cdlC.getCount());

//        cdlB.countDown();
        cdlC.countDown();
//        System.out.println("cdlB = "+cdlB.getCount());
        System.out.println("cdlC = "+cdlC.getCount());
        System.out.println("*******************");
    }
    public synchronized void second (Runnable r){
        System.out.println("Second was called by "+Thread.currentThread().getName());
        System.out.println("second()");
//        System.out.println("cdlB = "+cdlB.getCount());
        System.out.println("cdlC = "+cdlC.getCount());
//        try {
//            cdlB.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        cdlC.countDown();
//        System.out.println("cdlB = "+cdlB.getCount());
        System.out.println("cdlC = "+cdlC.getCount());
        System.out.println("*******************");
    }
    public synchronized void third (Runnable r){
        System.out.println("Third was called by "+Thread.currentThread().getName());
        System.out.println("third cdlB.await()");
//        System.out.println("cdlB = "+cdlB.getCount());
        System.out.println("cdlC = "+cdlC.getCount());
//        try {
//            cdlB.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("third cdlC.await()");
//        System.out.println("cdlB = "+cdlB.getCount());
        System.out.println("cdlC = "+cdlC.getCount());
        try {
            cdlC.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        cdlC = new CountDownLatch(2);
//        System.out.println("cdlB = "+cdlB.getCount());

        System.out.println("*******************");
    }

}
