package part1.lesson03.task03;

import java.util.HashSet;
import java.util.Set;

/**
 * @author KhafizovAR
 */
public class ObjectBox<T> {
    Set<T> objectCollection = new HashSet<>();

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
        return "ObjectBox [objectCollection=" + objectCollection + "]";
    }
}
