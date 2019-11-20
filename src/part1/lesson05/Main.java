package part1.lesson05;

import helpers.RandomDataGenerator;
import part1.lesson05.pojo.Person;
import part1.lesson05.pojo.Pet;

import java.util.Optional;
import java.util.Random;

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
    public static final int ARR_SIZE = 1000;
    private static Random rnd = new Random();
    public static void main(String[] args) throws PseudoStore.RowExistException {

        //генерация данных
        for(int i = 0; i< ARR_SIZE; i++) {
            Pet p1 = new Pet(RandomDataGenerator.getRandomString(5),
                    new Person(rnd.nextInt(100),
                            RandomDataGenerator.getRandomString(6),
                            rnd.nextBoolean() ? Person.SexEnum.MAN : Person.SexEnum.WOMAN),
                    RandomDataGenerator.getRandomDoubleBetweenRange(0,100)
                    );
            PseudoStore.add(p1);
        }
        //Сортировка и печать
        PseudoStore.printItems();
        //Добавление
        PseudoStore.add(new Pet("name 1",
                new Person(20,"p1", Person.SexEnum.MAN),
                10D
        ));
        //Поиск ранее добавленного по имени
        System.out.println("-------------поиск по имени----------");

        Optional<Pet> p = PseudoStore.findByPetName("name 1");
        System.out.println(p);
        System.out.println("--------------апдейт по UUID--------");
        PseudoStore.updatePetByUUID(p.get().getId(),
                RandomDataGenerator.getRandomString(6),
                new Person(rnd.nextInt(100),
                        RandomDataGenerator.getRandomString(6),
                        rnd.nextBoolean() ? Person.SexEnum.MAN : Person.SexEnum.WOMAN),
                null);
        System.out.println("Новые значения для ранее найденного:" + PseudoStore.findByPetName("name 1"));
        System.out.println("--------------попытка записи двух одинаковых элементов --------");
        //PseudoStore.add(p1);

        PseudoStore.add(new Pet("name 2",
                new Person(30,"p1", Person.SexEnum.MAN),
                10D
        ));
        PseudoStore.add(new Pet("name 2",
                new Person(30,"p1", Person.SexEnum.MAN),
                10D
        ));

    }
}
