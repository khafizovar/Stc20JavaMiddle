package part1.lesson11.task1;

import com.sun.org.apache.xpath.internal.functions.Function2Args;
import part1.lesson11.NumAndBool;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

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

        Function<Integer, BigInteger> nfWithCache = (n) -> {
            if (n == 0)
                return BigInteger.ONE;
            if (Cache.getValueByKey(n).isPresent()) {
                return Cache.getValueByKey(n).get();
            }
            BigInteger ret = BigInteger.ONE;
            if (n >= 1) {
                //Поиск наибольшего близкого
                int i = Cache.findNearest(n);
                if(Cache.getValueByKey(i).isPresent()) {
                    ret = Cache.getValueByKey(i).get();
                    i++;
                } else
                    i = 1;
                for (; i <= n; i++) {
                    ret = ret.multiply(BigInteger.valueOf(i));
                }
            }
            Cache.put(n, ret);
            return ret;
        };
        //ДЗ-11
        Function<Integer, BigInteger> nfWithoutCache = (n) -> {
            if (n == 0)
                return BigInteger.ONE;
            if (Cache.getValueByKey(n).isPresent()) {
                return Cache.getValueByKey(n).get();
            }
            BigInteger ret = BigInteger.ONE;
            if (n >= 1) {
                for (int i = 1; i <= n; i++) {
                    ret = ret.multiply(BigInteger.valueOf(i));
                }
            }
            Cache.put(n, ret);
            return ret;
        };

        System.out.println("Генерация массива, размер:" + ARR_SIZE);
        //ДЗ-11, функциональный интерфейс
        BiFunction<Integer, Integer, Integer> rndNumber = (min, max) -> ((Double)(((Math.random()*(max-min)+1))+min)).intValue();

        for (int i=0; i<ARR_SIZE; i++) {
            numbers[i] = rndNumber.apply(MIN_VAL, MAX_VAL);
        }

        //ДЗ-11
        NumAndBool nma = (int [] nums, Function<Integer, BigInteger> fn) -> {
            List<Thread> threads = new ArrayList<>();
            for (int n : nums) {
                //ДЗ-11
                //Function<Integer, BigInteger> fn = (bool) ? nfWithoutCache : nfWithCache;
                Thread thr = new Thread(() -> new Factorial(n, fn));
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
        Map<Integer, BigInteger> dataFirst  =  nma.run(numbers, nfWithoutCache);
        System.out.println( "With Thread:" + (System.currentTimeMillis() - beginTime) + "мс");
        Cache.clearCache();
        beginTime = System.currentTimeMillis();
        Map<Integer, BigInteger> dataSecond = nma.run(numbers, nfWithCache);
        System.out.println( "With Thread and search:" + (System.currentTimeMillis() - beginTime) + "мс");

        //ДЗ-11
        nma = (int [] nums, Function<Integer, BigInteger> nf) -> {
            Map<Integer, BigInteger> res = new HashMap<>();
            for (int n : nums) {
                res.put(n, nf.apply(n));
            }
            return res;
        };

        //ДЗ-11
        Function<Integer, BigInteger> nf = (Integer n) -> {
            BigInteger result = BigInteger.valueOf(1);
            for (int i = 1; i <=n; i++){
                result = result.multiply(BigInteger.valueOf(i));
            }
            return result;
        };


        beginTime = System.currentTimeMillis();
        Map<Integer, BigInteger> dataThird =  nma.run(numbers, nf);
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
