package part1.lesson07;

import java.math.BigInteger;
import java.util.*;

/**
 * Дан массив случайных чисел. Написать программу для вычисления факториалов всех элементов массива. Использовать пул потоков для решения задачи.
 *
 * Особенности выполнения:
 *
 * Для данного примера использовать рекурсию - не очень хороший вариант, т.к. происходит большое выделение памяти, очень вероятен StackOverFlow.
 * Лучше перемножать числа в простом цикле при этом создавать объект типа BigInteger
 * По сути, есть несколько способа решения задания:
 * 1) распараллеливать вычисление факториала для одного числа
 * 2) распараллеливать вычисления для разных чисел
 * 3) комбинированный
 *
 * При чем вычислив факториал для одного числа, можно запомнить эти данные и использовать их для вычисления другого, что будет гораздо быстрее
 * @author KhafizovAR on 15.11.2019.
 * @project Stc20JavaMiddle
 */
public class Main {
    private static final int ARR_SIZE = 10000;
    private static final int MAX_VAL = 3000;
    private static final int MIN_VAL = 1000;

    public static void main(String[] args) {
        int [] numbers = new int [ARR_SIZE];
        System.out.println("Генерация массива, размер:" + ARR_SIZE);
        for (int i=0; i<ARR_SIZE; i++) {
            numbers[i] = getRandomDoubleBetweenRange(MIN_VAL, MAX_VAL);
        }
        long beginTime = System.currentTimeMillis();
        Map<Integer, BigInteger> dataFirst  =  factorialCalculateRunner(numbers, false);
        System.out.println( "With Thread:" + (System.currentTimeMillis() - beginTime) + "мс");
        Factorial.clearCache();
        beginTime = System.currentTimeMillis();
        Map<Integer, BigInteger> dataSecond = factorialCalculateRunner(numbers, true);
        System.out.println( "With Thread and search:" + (System.currentTimeMillis() - beginTime) + "мс");
        beginTime = System.currentTimeMillis();
        Map<Integer, BigInteger> dataThird =  calculateWithoutTread(numbers);
        System.out.println( "Without Thread:" + (System.currentTimeMillis() - beginTime) + "мс");

        //Ну и проверим, на предмет расхождений
        dataFirst.forEach((integer, bigInteger) -> {
            if(dataSecond.get(integer) == null || !dataSecond.get(integer).equals(bigInteger)) {
                System.out.println("Расхождение результата!!");
            }
        });

    }

    /**
     * Метод запуска потоков по расчету факториала, этот же метод ожидает окончания потоков
     * @param numbers  Числа для расчета факториала
     * @return
     */
    private static Map<Integer, BigInteger> factorialCalculateRunner(int [] numbers, boolean s) {
        List<Thread> threads = new ArrayList<>();
        for (int n : numbers) {
            Thread thread = new Thread(new Factorial(n, s));
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Factorial.getCache();
    }

    /**
     * Метод запуска расчета факториала в лоб
     * @param numbers   массив чисел
     * @return
     */
    private static Map<Integer, BigInteger> calculateWithoutTread(int [] numbers) {
        Map<Integer, BigInteger> res = new HashMap<>();
        for (int n : numbers) {
            res.put(n, Factorial.calculateFactorial(n));
        }
        return res;
    }

    /**
     * Генерация числа от до
     * @param min   минимальная граница
     * @param max   максимальная граница
     * @return  случайное число
     */
    private static int getRandomDoubleBetweenRange(int min, int max){
        int x = (int) ((Math.random()*((max-min)+1))+min);
        return x;
    }
}
