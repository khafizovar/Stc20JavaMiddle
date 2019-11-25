package part1.lesson11.task1;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author KhafizovR by 24.11.2019
 * Stc20JavaMiddle
 */
public class Cache {
    /**
     * Кэш расчитанных значений
     */
    private static final Map<Integer, BigInteger> cache = new ConcurrentHashMap<Integer,BigInteger>();

    /**
     * Получить кэш
     * @return
     */
    static Map<Integer,BigInteger> getCache() {
        return new HashMap<Integer,BigInteger>(cache);
    }

    /**
     * Получить значение по ключу
     * @param i
     * @return
     */
    static Optional<BigInteger> getValueByKey(Integer i) {
        return Optional.ofNullable(cache.get(i));
    }

    /**
     * Очитска кэша
     */
    static void clearCache() {
        cache.clear();
    }

    /**
     * Добавление записи в КЭШ
     * @param key
     * @param value
     */
    static void put(Integer key, BigInteger value) {
        cache.put(key, value);
    }

    /**
     * Найти ближайший ключ
     * @param key
     * @return
     */
    static Integer findNearest (Integer key) {
        Iterator it = cache.entrySet().iterator();
        int nearestDistance = Integer.MAX_VALUE;
        int i = 1;
        while (it.hasNext()) {
            Map.Entry<Integer, BigInteger> pair = (Map.Entry)it.next();
            if(pair.getKey() < key && key - pair.getKey() < nearestDistance) {
                nearestDistance = key - pair.getKey();
                i = pair.getKey();
            }
        }
        if(nearestDistance != Integer.MAX_VALUE) {
            return  i;
        }
        return -1;
    }
}
