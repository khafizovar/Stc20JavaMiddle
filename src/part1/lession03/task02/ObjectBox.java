package part1.lession03.task02;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((objectCollection == null) ? 0 : objectCollection.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ObjectBox other = (ObjectBox) obj;
        if (objectCollection == null) {
            if (other.objectCollection != null)
                return false;
        } else if (!objectCollection.equals(other.objectCollection))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");
        for (Object obj:
                objectCollection) {
            s.append(obj.toString() + ";");
        }
        s.replace(s.length() - 2,s.length()-1, "}");
        return s.toString();
    }
}
