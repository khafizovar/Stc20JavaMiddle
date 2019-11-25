package part1.lesson11.task1;

import part1.lesson11.NumAndBool;
import part1.lesson11.NumericFunc;
import part1.lesson11.TwoArgNumericFunc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //ДЗ-11, функциональный интерфейс
        TwoArgNumericFunc rndNumber = (min, max) -> (int) ((Math.random()*((max-min)+1))+min);

        for (int i=0; i<ARR_SIZE; i++) {
            numbers[i] = rndNumber.getRandomBetween(MIN_VAL, MAX_VAL);
        }

        //ДЗ-11
        NumAndBool nma = (int [] nums, Boolean bool) -> {
            List<Thread> threads = new ArrayList<>();
            for (int n : nums) {
                //ДЗ-11
                Thread thr = new Thread(() -> new Factorial(n, false));
                thr.start();
                threads.add(thr);
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return Cache.getCache();
        };

        long beginTime = System.currentTimeMillis();
        Map<Integer, BigInteger> dataFirst  =  nma.run(numbers, false);
        System.out.println( "With Thread:" + (System.currentTimeMillis() - beginTime) + "мс");
        Cache.clearCache();
        beginTime = System.currentTimeMillis();
        Map<Integer, BigInteger> dataSecond = nma.run(numbers, true);
        System.out.println( "With Thread and search:" + (System.currentTimeMillis() - beginTime) + "мс");

        //ДЗ-11
        nma = (int [] nums, Boolean bool) -> {
            Map<Integer, BigInteger> res = new HashMap<>();
            //ДЗ-11
            NumericFunc nf = (int n) -> {
                BigInteger result = BigInteger.valueOf(1);
                for (int i = 1; i <=n; i++){
                    result = result.multiply(BigInteger.valueOf(i));
                }
                return result;
            };
            for (int n : nums) {
                res.put(n, nf.func(n));
            }
            return res;
        };


        beginTime = System.currentTimeMillis();
        Map<Integer, BigInteger> dataThird =  nma.run(numbers, null);
        System.out.println( "Without Thread:" + (System.currentTimeMillis() - beginTime) + "мс");

        //ДЗ-11 Проверка на предмет расхождений
        long diffCount = dataFirst.entrySet()
                .stream()
                .filter(k -> ((dataThird.get(k.getKey()) == null || !dataThird.get(k.getKey()).equals(k.getValue())))).count();
        if(diffCount > 0) {
            System.out.println("Расхождение результата!!");
        } else {
            System.out.println("Расхождений не найдено");
        }
    };
}
