# Module_1_4_Dmitry_Elkin_MultiThread

Практика:
Задание
Дан класс:
public class Foo {
  public void first(Runnable r) { print("first"); }
  public void second(Runnable r) { print("second"); }
  public void third(Runnable r) { print("third"); }
}
Один и тот же экземпляр данного класса будет вызван 3мя разными потоками. Поток А - будет вызывать метод first(). Поток B - second(). Поток С - third(). 
Необходимо реализовать механизм и изменить программу таким образом, что методы класса Foo будут вызваны в правильном порядке.
Пример:
Вывод: "firstsecondthird"
Мы не знаем, в каком порядке будут вызваны методы, но должны гарантировать порядок.

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
    latchB в этот момент равняется 1 и запрещает запуск метода second - т.е. может быть запущен только first/
