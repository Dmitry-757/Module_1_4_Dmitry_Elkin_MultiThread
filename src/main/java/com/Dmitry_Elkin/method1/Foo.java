package com.Dmitry_Elkin.method1;

import java.util.concurrent.CountDownLatch;

/**
 Реализация:
 - все три метода (first, second, third) объявляем synchronized, монитор блокировки для каждого метода устанавливаем свой.
 - к каждому синхронизируемому методу прикручиваем свою "защелку" CountDawnLatch и в начале метода вставляем контроль await.
 - при старте для метода first устанавливается latch=0, для second latch=1, для third latch = 2.

 Основная идея синхронизации:
 - при старте метод first может быть вызван сразу (у него latch = 0), далее в теле это метода
 срабатывает метод countDown для latchB - защелки метода second чем вызывает возможность входа потока в метод second
 (метод fird остается заблокированным - у него latchC = 1).
 Так же "обновляется" защелка latchA=1 - блокируется вызов метода first
 - единственный метод, у которого защелка == 1 это second. В нем вызывается countDown для latchC (latchC становится равным 0 и снимается
 блокировка для потока заускающего метод third). Тут же обновляется защелка для метода second - latchB устанавливается = 1.
 Таким образом на данный момент latchA=1, latchB=1, latchC=0 - возможен запуск только метода third
 - поток (если еще не пытался входить в метод third) входит туда ( а если пытался и "замерз" на блоке await - разблокируется) и выполняет
 печать а так же "взводит" latchC = 1 и выполняет countDown для latchA, latchA становится равным 0 и появляется возможность запустить метод first,
 latchB в этот момент равняется 1 и запрещает запуск метода second - т.е. может быть запущен только first.

 */

public class Foo {
    CountDownLatch cdlA;
    CountDownLatch cdlB;
    CountDownLatch cdlC;
    private final Object lock1;
    private final Object lock2;
    private final Object lock3;





    public Foo(CountDownLatch cdlA, CountDownLatch cdlB, CountDownLatch cdlC) {
        this.cdlA = cdlA;
        this.cdlB = cdlB;
        this.cdlC = cdlC;

        //вопрос - почему все будет блокироваться  на third() и не пойдет дальше если не использовать lock1,2,3 а простые блоки synchronized?
        this.lock1 = new Object();
        this.lock2 = new Object();
        this.lock3 = new Object();
    }

    public void first (Runnable r){
        synchronized(lock1) {
            try {
                cdlA.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("First was called by " + Thread.currentThread().getName());

            cdlB.countDown();
//            cdlC.countDown();
            cdlA = new CountDownLatch(1);

        }
    }
    public  void second (Runnable r){
        synchronized(lock2) {
            try {
                cdlB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Second was called by " + Thread.currentThread().getName());

            cdlC.countDown();
            cdlB = new CountDownLatch(1);
        }
    }
    public  void third (Runnable r){
        synchronized(lock3) {
            try {
                cdlC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Third was called by " + Thread.currentThread().getName());
            cdlC = new CountDownLatch(1);
            cdlA.countDown();
        }
    }

}
