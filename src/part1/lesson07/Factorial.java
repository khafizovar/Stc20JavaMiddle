package part1.lesson07;

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

    Factorial(int n, boolean searchInCache) { this.n = n; this.searchInCache = searchInCache; }
    /**
     * Метод расчета факториала с кэшированием ранее расчитанных + участие в расчете.
     * @return
     */
    BigInteger factorialWithCalculate() {
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
    }

    /**
     * Метод расчета факториала с учетом ранее расчитанных, но без участия в расчете
     * @return
     */
    BigInteger factorial() {
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
    }



    /**
     * Метод расчета факториала в лоб, без обращения и сохранений в кэше
     * @param n число для которого необходимо расчитать факториал
     * @return  расчитанный факториал
     */
    static BigInteger calculateFactorial(int n){
        BigInteger result = BigInteger.valueOf(1);
        for (int i = 1; i <=n; i ++){
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    @Override
    public void run() {
        if(!searchInCache)
            this.factorial();
        else
            this.factorialWithCalculate();
    }


}
