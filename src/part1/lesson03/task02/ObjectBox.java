package part1.lesson03.task02;

import java.util.ArrayList;
import java.util.List;

/**
 * Задание 2. Создать класс ObjectBox, который будет хранить коллекцию Object.
 * У класса должен быть метод addObject, добавляющий объект в коллекцию.
 * У класса должен быть метод deleteObject, проверяющий наличие объекта в коллекции и при наличии удаляющий его.
 * Должен быть метод dump, выводящий содержимое коллекции в строку.
 * @author KhafizovAR
 */
public class ObjectBox {
    List<Object> objectCollection = new ArrayList<>();

    /**
     * Добавление объекта в коллекцию
     * @param obj  Добавляемый объект
     */
    public void addBox(Object obj) {
        this.objectCollection.add(obj);
    }

    /**
     * Удаление объекта из коллекции
     * @param obj   удаляемый объект
     */
    public void deleteObject(Object obj) {
        this.objectCollection.remove(obj);
    }

    /**
     * Возвращает строку с содержимым коллекци
     * @return
     */
    public String dump() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "ObjectBox [objectCollection=" + objectCollection + "]";
    }
}
