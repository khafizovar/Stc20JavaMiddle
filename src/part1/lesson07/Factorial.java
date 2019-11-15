package part1.lesson07;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс по расчету факториала для запуска в потоке
 * @author KhafizovAR on 15.11.2019.
 * @project Stc20JavaMiddle
 */
public class Factorial implements Runnable {
    /**
     * Кэш расчитанных значений
     */
    private static volatile Map<Integer,BigInteger> cache = new ConcurrentHashMap<Integer,BigInteger>();

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
        if (null != cache.get(n)) {
            return cache.get(n);
        }
        BigInteger ret = BigInteger.ONE;
        if (n >= 1) {
            //Поиск наибольшего близкого
            Iterator it = cache.entrySet().iterator();
            int nearestDistance = Integer.MAX_VALUE;
            int i = 1;
            while (it.hasNext()) {
                Map.Entry<Integer, BigInteger> pair = (Map.Entry)it.next();
                if(pair.getKey() < n && n - pair.getKey() < nearestDistance) {
                    nearestDistance = n - pair.getKey();
                    i = pair.getKey();
                }
            }
            if(nearestDistance != Integer.MAX_VALUE) {
                ret = cache.get(i);
            }
            i++;    //Текущий факториал уже расчитан, считаем следующий
            for (; i <= n; i++) {
                ret = ret.multiply(BigInteger.valueOf(i));
            }
        }
        cache.put(n, ret);
        return ret;
    }

    /**
     * Метод расчета факториала с учетом ранее расчитанных, но без участия в расчете
     * @return
     */
    BigInteger factorial() {
        if (n == 0)
            return BigInteger.ONE;
        if (null != cache.get(n)) {
            return cache.get(n);
        }
        BigInteger ret = BigInteger.ONE;
        if (n >= 1) {
            for (int i = 1; i <= n; i++) {
                ret = ret.multiply(BigInteger.valueOf(i));
            }
        }
        cache.put(n, ret);
        return ret;
    }

    static Map<Integer,BigInteger> getCache() {
        return new HashMap<Integer,BigInteger>(cache);
    }

    static void clearCache() {
        cache.clear();
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
