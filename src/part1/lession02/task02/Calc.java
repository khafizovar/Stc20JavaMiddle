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
    private final Random random = new Random();
     /**
     *
     * @param n количество случайных чисел
     * @return Результат вычислений {@link CalcResult}
     * @throws Exception
     */
    public CalcResult calculate(int n) throws IllegalArgumentException {
        if (n < 1) {
            throw new IllegalArgumentException("Для генерации параметр n должен быть больше 0");
        }
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            numbers.add(random.nextInt());

        List<Integer> res = new ArrayList<Integer>();
        List<Integer> wrongNumbers = new ArrayList<Integer>();
        for (Integer k : numbers) {
            try {
                if (this.isSatisfy(k))
                    res.add(k);
            } catch (NegativeNumberException ex) {
                wrongNumbers.add(k);
            }
        }
        return new CalcResult(res, wrongNumbers);

    }

    /**
     * Проверка удовлетворяет ли число условию
     * @param k число для анализа
     * @return  true|false удовлетворяет|неудовлетворяет
     * @throws NegativeNumberException
     */
    private boolean isSatisfy(int k) throws NegativeNumberException {
        if (k < 0) {
            throw new NegativeNumberException("Найдено отрицательное число");
        }
        Double q = Math.sqrt(k);
        return (Math.pow(q.intValue(), 2) == k);
    }

    /* Ниже привел комментарий в телеграмм-чате от преподавателя курса, поэтому и добавил генерацию исключения и его перехват, возможно я неверно интерпритировал его слова
     * "Исключение должно генерировать, если попало хотя бы одно отрицательное число, приложение не должно останавливаться и должно продолжать работу."
     */
    public static void main(String[] args) throws Exception {
        Calc c = new Calc();
        CalcResult res = c.calculate(10000);
        if(res.getSatisfyDigits() != null && res.getSatisfyDigits().size() > 0) {
            res.getSatisfyDigits().forEach(item -> System.out.println(item));
        } else {
            System.out.println("Чисел удовлетворяющих критерию не найдено");
        }

        if(res.getNegativeNumbers() != null && res.getNegativeNumbers().size()  > 0) {
            System.out.println("Найденные отрицательные числа:");
            res.getNegativeNumbers().forEach(item -> System.out.println(item));
        }
    }

    /**
     * Класс-исключение для отрицательных чисел
     */
    class NegativeNumberException extends Exception {
        public NegativeNumberException(String message) {
            super(message);
        }
    }
}
