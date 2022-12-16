package com.Dmitry_Elkin.method2;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        Foo foo = new Foo();

        CompletableFuture.runAsync(() -> {
            foo.third(new Thread());
        });

        CompletableFuture.runAsync(() -> {
            foo.second(new Thread());
        });

        CompletableFuture.runAsync(() -> {
            foo.first(new Thread());
        });

    }
}

class Foo {
    public void first(Runnable r) {
        System.out.println("first");

    }
    public void second(Runnable r) {
        System.out.println("second");
    }
    public void third(Runnable r) {

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("third");
    }

}