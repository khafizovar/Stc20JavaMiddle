package helpers;

public class Helper {

    public static final String ARR_SIZE_EX_MESS = "Размер массива должен быть больше либо равен 1";

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
    public static Number[] getArrayOfRandomDoubles(int size, double min, double max) throws Exception {
        if(size < 1) {
            throw new Exception(ARR_SIZE_EX_MESS);
        }
        Number[] n = new Number [size];

        for(int i=0; i < n.length; i++)
            n[i] = Helper.getRandomDoubleBetweenRange(min, max);
        return n;
    }

    /**
     * Генерация массива слуайных чисел типа @int
     * @param size размер массива
     * @param min   минимальное значение массива
     * @param max   максимальное значение массива
     * @return      массив чисел типа @int
     * @throws Exception
     */
    public static Number[] getArrayOfRandomInts(int size, int min, int max) throws Exception {
        if(size < 1) {
            throw new Exception("Размер массива должен быть больше либо равен 1");
        }
        Number[] n = new Number [size];

        for(int i=0; i < n.length; i++)
            n[i] = (int)Helper.getRandomDoubleBetweenRange(min, max);
        return n;
    }

    /**
     * hex строка в массив байтов
     * @param s
     * @return
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
