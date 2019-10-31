package part1.lession02.task03;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * Задание 3. Дан массив объектов Person. Класс Person характеризуется полями age (возраст, целое число 0-100), sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN), name (имя - строка). Создать два класса, методы которых будут реализовывать сортировку объектов. Предусмотреть единый интерфейс для классов сортировки. Реализовать два различных метода сортировки этого массива по правилам:
 * первые идут мужчины
 * выше в списке тот, кто более старший
 * имена сортируются по алфавиту
 * Программа должна вывести на экран отсортированный список и время работы каждого алгоритма сортировки.
 * Предусмотреть генерацию исходного массива (10000 элементов и более).
 * Если имена людей и возраст совпадают, выбрасывать в программе пользовательское исключение.
 */
public class Main {
    private static final int ARR_SIZE = 10;
    private static final Random rnd = new Random();

    static String getAlphaNumericString(int n) {
        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {

            char ch = randomString.charAt(k);

            if (((ch >= 'a' && ch <= 'z')
                    || (ch >= 'A' && ch <= 'Z'))
                    && (n > 0)) {

                r.append(ch);
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }

    public static void main(String[] args) {
        long tmpDate;
        Sort firstTypeSort = new FirstSort();
        Sort secondTypeSort = new SecondSort();

        /* Генерация данных */
        Person[] persons = new Person[ARR_SIZE];
        for (int i = 0; i < ARR_SIZE; i++) {
            Person p = new Person(rnd.nextInt(100),
                    Main.getAlphaNumericString(1),
                    (rnd.nextInt(2) == 1) ? Person.SexEnum.MAN : Person.SexEnum.WOMAN);
            persons[i] = p;
        }
        System.out.println("-------------original data----------");
        /*for (Person p: persons) {
            System.out.println(p.toString());
        }*/
        System.out.println("-------------first sort ----------");
        tmpDate = System.currentTimeMillis();
        Person[] sorted1 = firstTypeSort.sort(persons);
        System.out.println("Затрачено:" + (System.currentTimeMillis() - tmpDate) + " мс");
        for (Person p: sorted1) {
            System.out.println(p.toString());
        }

        System.out.println("-------------second sort ----------");
        tmpDate = System.currentTimeMillis();
        Person[] sorted2 = secondTypeSort.sort(persons);
        System.out.println("Затрачено:" + (System.currentTimeMillis() - tmpDate) + " мс");
        for (Person p : sorted2) {
            System.out.println(p.toString());
        }
        //persons.forEach(p -> System.out.println(p));
    }
}
