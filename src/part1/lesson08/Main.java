package part1.lesson08;

import part1.lesson08.entity.Human;
import part1.lesson08.entity.Pet;

import java.util.jar.JarOutputStream;

/**
 * Задание 1. Необходимо разработать класс, реализующий следующие методы:
 *
 * void serialize (Object object, String file);
 *
 * Object deSerialize(String file);
 *
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла. Обязательна сериализация и десериализация "плоских" объектов (все поля объекта - примитивы, или String).
 *
 * Задание 2. Предусмотреть работу c любыми типами полей (полями могут быть ссылочные типы).
 * @author KhafizovAR on 18.11.2019.
 * @project Stc20JavaMiddle
 */
public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        Human human = new Human("землянин", "Вася Пупкин", 200, 100000, new Pet("Кеша", 200D));
        System.out.println(human);
        SimpleSerializer ss = new SimpleSerializer();
        ss.serialize(human, "files/Human2.bin");
        Object obj = ss.deSerialize("files/Human2.bin");
        System.out.println(obj);
    }

}
