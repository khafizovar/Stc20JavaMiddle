package part1.lession02.task03;

import java.util.Random;

/**
 * Задание 3. Дан массив объектов Person. Класс Person характеризуется полями age (возраст, целое число 0-100), sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN), name (имя - строка). Создать два класса, методы которых будут реализовывать сортировку объектов. Предусмотреть единый интерфейс для классов сортировки. Реализовать два различных метода сортировки этого массива по правилам:
 * первые идут мужчины
 * выше в списке тот, кто более старший
 * имена сортируются по алфавиту
 * Программа должна вывести на экран отсортированный список и время работы каждого алгоритма сортировки.
 * Предусмотреть генерацию исходного массива (10000 элементов и более).
 * Если имена людей и возраст совпадают, выбрасывать в программе пользовательское исключение.
 * @author KhafizovAR
 */
public class Main {
    private static final int ARR_SIZE = 10000;
    private static final Random rnd = new Random();

    /**
     * Генерация случайной строки
     * @param targetStringLength  длинна генерируемой строки
     * @return
     */
    static String getRandomString(int targetStringLength) {
        int aDecCode = 97;
        int zDecCode = 122;
        StringBuilder sb = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = aDecCode + (int)
                    (rnd.nextFloat() * (zDecCode - aDecCode + 1));
            sb.append((char) randomLimitedInt);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Sort firstTypeSort = new SelectionSort();
        Sort secondTypeSort = new BubbleSort();

        /* Генерация данных */
        Person[] persons = new Person[ARR_SIZE];
        for (int i = 0; i < ARR_SIZE; i++) {
            Person p = new Person(rnd.nextInt(100),
                    Main.getRandomString(5),
                    rnd.nextBoolean() ? Person.SexEnum.MAN : Person.SexEnum.WOMAN);
            persons[i] = p;
        }
        System.out.println("Количество элементов: " + ARR_SIZE);
        System.out.println("-------------selection sort ----------");
        long tmpDate = System.currentTimeMillis();
        Person[] sorted1 = firstTypeSort.sort(persons);
        System.out.println("Затрачено:" + (System.currentTimeMillis() - tmpDate) + " мс");
        printInfo(sorted1);

        System.out.println("-------------bubble sort ----------");
        tmpDate = System.currentTimeMillis();
        Person[] sorted2 = secondTypeSort.sort(persons);
        System.out.println("Затрачено:" + (System.currentTimeMillis() - tmpDate) + " мс");
        printInfo(sorted2);

        System.out.println("------------дубли по возрасту и имени");
        persons = new Person[4];
        persons[0] = new Person(5, "a", Person.SexEnum.WOMAN );
        persons[1] = new Person(5, "b", Person.SexEnum.MAN );
        persons[2] = new Person(5, "b", Person.SexEnum.MAN );
        persons[3] = new Person(5, "c", Person.SexEnum.MAN );
        firstTypeSort.sort(persons);
    }

    /**
     * Вывпод в консоль массива построчно
     * @param array объект для вывода в консоль
     */
    static void printInfo(Person[] array) {
        for (Person p: array) {
            System.out.println(p.toString());
        }
    }
}
