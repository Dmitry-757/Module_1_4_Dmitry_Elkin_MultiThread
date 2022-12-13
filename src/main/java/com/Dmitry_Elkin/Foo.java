package com.Dmitry_Elkin;

public class Foo {
    public synchronized void first (Runnable r){
        System.out.println("First");
        System.out.println(" was called by "+Thread.currentThread().getName());
    }
    public synchronized void second (Runnable r){
        System.out.println("Second");
        System.out.println(" was called by "+Thread.currentThread().getName());
    }
    public synchronized void third (Runnable r){
        System.out.println("Third");
        System.out.println(" was called by "+Thread.currentThread().getName());
    }

}
