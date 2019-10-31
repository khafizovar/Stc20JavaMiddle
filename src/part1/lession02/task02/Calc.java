package part1.lession02.task02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Задание 2. Составить программу, генерирующую N случайных чисел. Для каждого числа k вычислить квадратный корень q.
 * Если квадрат целой части q числа равен k, то вывести это число на экран. Предусмотреть что первоначальные числа могут быть отрицательные,
 * в этом случае генерировать исключение.
 */
public class Calc {
    //private static final int MAX_VALUE = 10;
    final Random random = new Random();
    /**
     * @param n количество случайных чисел
     */
    public void calculate(int n) throws Exception {
        if(n < 1) {
            throw new Exception("Для генерации параметр n должен быть больше 0");
        }
        List<Integer> numbers = new ArrayList<Integer>();
        for(int i=0; i<n; i++)
            numbers.add(random.nextInt());

        for (Integer k  :numbers) {
            if(k < 0) {
                throw new Exception("Найдено отрицательное число");
            }
            Double q = Math.sqrt(k);

            if(Math.pow(q.intValue(),2) == k) {
                System.out.println(k);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Calc c = new Calc();
        c.calculate(10);
    }
}
