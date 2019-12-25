package part1.lesson03.task03;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author KhafizovAR
 */
public class ObjectBox<T> {
    protected List<T> objectCollection = new LinkedList<>();
    /**
     * Добавление объекта в коллекцию
     * @param obj  Добавляемый объект
     */
    public void addBox(T obj) {
        this.objectCollection.add(obj);
    }

    /**
     * Удаление объекта из коллекции
     * @param obj   удаляемый объект
     */
    public void deleteObject(T obj) {
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
