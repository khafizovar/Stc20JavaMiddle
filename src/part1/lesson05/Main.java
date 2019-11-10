package part1.lesson05;

import helpers.Helper;
import part1.lesson05.pojo.Person;
import part1.lesson05.pojo.Pet;

import java.util.Random;
import java.util.UUID;

/**
 * @author KhafizovR by 10.11.2019
 * Stc20JavaMiddle
 *
 * Разработать программу – картотеку домашних животных. У каждого животного есть уникальный идентификационный номер, кличка, хозяин (объект класс Person с полями – имя, возраст, пол), вес.
 *
 * Реализовать:
 *
 * метод добавления животного в общий список (учесть, что добавление дубликатов должно приводить к исключительной ситуации)
 * поиск животного по его кличке (поиск должен быть эффективным)
 * изменение данных животного по его идентификатору
 * вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
 */
public class Main {
    public static final int ARR_SIZE = 5;
    private static Random rnd = new Random();
    public static void main(String[] args) throws PseudoStore.RowExistException {
        PseudoStore store = new PseudoStore();

        //генерация данных
        for(int i = 0; i< ARR_SIZE; i++) {
            Pet p1 = new Pet(UUID.randomUUID(),
                    Helper.getRandomString(5),
                    new Person(rnd.nextInt(100),
                            Helper.getRandomString(6),
                            rnd.nextBoolean() ? Person.SexEnum.MAN : Person.SexEnum.WOMAN),
                    Helper.getRandomDoubleBetweenRange(0,100)
                    );
            store.add(p1);
        }

        store.printItems();
        UUID uuid = UUID.randomUUID();
        Pet p1 = new Pet(uuid,"name 1",
                new Person(rnd.nextInt(100),
                        Helper.getRandomString(6),
                        rnd.nextBoolean() ? Person.SexEnum.MAN : Person.SexEnum.WOMAN),
                10D
        );
        store.add(p1);
        //Поиск
        System.out.println("-------------поиск по имени----------");
        long tmpDate = System.currentTimeMillis();
        store.findByPetName("name 1");
        System.out.println("Затрачено:" + (System.currentTimeMillis() - tmpDate) + " мс");
        System.out.println("--------------апдейт по UUID--------");
        Pet p2 = new Pet(uuid,"name 1",
                new Person(rnd.nextInt(100),
                        Helper.getRandomString(6),
                        rnd.nextBoolean() ? Person.SexEnum.MAN : Person.SexEnum.WOMAN),
                null
        );
        store.updatePetByUUID(p2);
        System.out.println("--------------попытка записи двух одинаковых элементов --------");
        store.add(p1);

    }
}
