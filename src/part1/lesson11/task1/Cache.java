package part1.lesson11.task1;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        int i = -1;
        //Поиск найболее близкого объекта снизу
        i = cache.entrySet().stream()
                .filter(pair-> pair.getKey() < key)
                .max(Comparator.comparingInt(Map.Entry::getKey))
                .get().getKey();
        return i;
    }
}
