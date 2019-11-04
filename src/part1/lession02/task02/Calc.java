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
    //мёртвый код
    private static final int MAX_VALUE = 10;
    //можно сделать приватным
    final Random random = new Random();
    /**
     * @param n количество случайных чисел
     * @return Массив из двух элементво. 0 -  {@link List} Содержит числа удволетворяющие услвоию, 1 - {@link List} отрицательные числа
     */
    //возвращение массива в надежде на то, что клиент будет знать какой элемент что значит - плохая практика. Усложняет выстраивание контрактов и затрудняет рефакторинг
    //лучше ввести специальную структуру данных для такого объекта
    public List[] calculate(int n) throws Exception {
        //переменные нужно определять как можно ближе к сместу использования. Если уж ставим валидацию, то в самом начале. А то инстанцируются два ненужных объекта и читабельность кода падает
        List<Integer> res = new ArrayList<Integer>();
        List<Integer> wrongNumbers = new ArrayList<Integer>();
        if (n < 1) {
            throw new Exception("Для генерации параметр n должен быть больше 0");
        }
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < n; i++)
            //закомментированный код
            //numbers.add(random.nextInt(MAX_VALUE));
            numbers.add(random.nextInt());

        for (Integer k : numbers) {
            try {
                if(this.isSatisfy(k))
                    res.add(k);
            } catch (Exception ex) {
                // то, что вылетело какое-то исключение не говорит о том, что это именно из-за отрицательного числа
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

        //можно написать короче
        if (Math.pow(q.intValue(), 2) == k) {
            return true;
        }
        return false;
    }

    // в задании требовалось не выводить правильные и неправильные числа, а по ходу выполнения выводить правильные
    // и выбросить исключение при первом попавшемся некорректном числе
    public static void main(String[] args) throws Exception {
        //подумайте, нужно ли в самом деле инстанцировать этот объект
        Calc c = new Calc();
        List[] res = c.calculate(10000);

        //учитывая то, что я написал выше про то, что можно ввести отдельный класс для ответа, подумайте,
        //как можно было бы ещё реализовать обработку ответа от метода
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
