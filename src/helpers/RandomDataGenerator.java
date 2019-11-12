package helpers;

import java.util.Random;

/**
 * Содержит методы генерации случаынйх данных, по определенным критериям
 * @author KhafizovAR on 12.11.2019.
 * @project Stc20JavaMiddle
 */
public class RandomDataGenerator {
    private static final Random rnd = new Random();
    /**
     * Генерация случайной строки
     * @param targetStringLength  длинна генерируемой строки
     * @return
     */
    public static String getRandomString(int targetStringLength) {
        int aCode = 97;
        int bCode = 122;
        StringBuilder str = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = aCode + (int)
                    (rnd.nextFloat() * (bCode - aCode + 1));
            str.append((char) randomLimitedInt);
        }
        return str.toString();
    }

    /**
     * Генерация числа от до
     * @param min   минимальная граница
     * @param max   максимальная граница
     * @return  случайное числочисло
     */
    public static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }

    /**
     * Генерация массива слуайных чисел типа @double
     * @param size размер массива
     * @param min   минимальное значение массива
     * @param max   максимальное значение массива
     * @return      массив чисел типа @double
     * @throws Exception
     */
    public static Number[] getArrayOfRandomDoubles(int size, double min, double max) {
        if(size < 1) {
            return new Number [0];
        }
        Number[] n = new Number [size];

        for(int i=0; i < n.length; i++)
            n[i] = Helper.getRandomDoubleBetweenRange(min, max);
        return n;
    }
}
