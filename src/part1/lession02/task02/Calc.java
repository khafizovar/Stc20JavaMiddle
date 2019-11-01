package part1.lession02.task02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Задание 2. Составить программу, генерирующую N случайных чисел. Для каждого числа k вычислить квадратный корень q.
 * Если квадрат целой части q числа равен k, то вывести это число на экран. Предусмотреть что первоначальные числа могут быть отрицательные,
 * в этом случае генерировать исключение.
 * @author KhafizovAR
 */
public class Calc {
    private static final int MAX_VALUE = 10;
    final Random random = new Random();
    /**
     * @param n количество случайных чисел
     * @return Массив из двух элементво. 0 -  {@link List} Содержит числа удволетворяющие услвоию, 1 - {@link List} отрицательные числа
     */
    public List[] calculate(int n) throws Exception {
        List<Integer> res = new ArrayList<Integer>();
        List<Integer> wrongNumbers = new ArrayList<Integer>();
        if (n < 1) {
            throw new Exception("Для генерации параметр n должен быть больше 0");
        }
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            //numbers.add(random.nextInt(MAX_VALUE));
            numbers.add(random.nextInt(MAX_VALUE));

        for (Integer k : numbers) {
            try {
                if(this.isSatisfy(k))
                    res.add(k);
            } catch (Exception ex) {
                wrongNumbers.add(k);
            }
        }

        return new List[]{res, wrongNumbers};
    }

    /**
     * Проверка удовлетворяет ли число условию
     * @param k число для анализа
     * @return  true|false удовлетворяет|неудовлетворяет
     * @throws Exception
     */
    private boolean isSatisfy(int k) throws Exception {
        if (k < 0) {
            throw new Exception("Найдено отрицательное число");
        }
        Double q = Math.sqrt(k);

        if (Math.pow(q.intValue(), 2) == k) {
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws Exception {
        Calc c = new Calc();
        List[] res = c.calculate(10000);
        if(res[0] != null && res[0].size() > 0) {
            res[0].forEach(item -> System.out.println(item));
        } else {
            System.out.println("Чисел удовлетворяющих критерию не найдено");
        }

        if(res[1] != null && res[1].size()  > 0) {
            System.out.println("Найденные отрицательные числа:");
            res[1].forEach(item -> System.out.println(item));
        }
    }
}
