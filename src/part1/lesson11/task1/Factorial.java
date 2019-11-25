package part1.lesson11.task1;

import part1.lesson11.NumericFunc;

import java.math.BigInteger;

/**
 * Класс по расчету факториала для запуска в потоке
 * @author KhafizovAR on 15.11.2019.
 * @project Stc20JavaMiddle
 */
public class Factorial implements Runnable {
    /** Число для расчета факториала */
    private final int n;
    /** Признак какой из методов использовать - с поиском по кэшу ближайшего */
    private final boolean searchInCache;
    //ДЗ-11
    private NumericFunc nfWithCache = (n) -> {
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
    private NumericFunc nfWithoutCache = (n) -> {
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

    Factorial(int n, boolean searchInCache) { this.n = n; this.searchInCache = searchInCache; }
    /**
     * Метод расчета факториала с кэшированием ранее расчитанных + участие в расчете.
     * @return
     */
    BigInteger factorialWithCalculate() {
        return nfWithCache.func(n);
    }

    /**
     * Метод расчета факториала с учетом ранее расчитанных, но без участия в расчете
     * @return
     */
    BigInteger factorial() {
        return nfWithoutCache.func(n);
    }

    @Override
    public void run() {
        if(!searchInCache)
            this.factorial();
        else
            this.factorialWithCalculate();
    }
}
