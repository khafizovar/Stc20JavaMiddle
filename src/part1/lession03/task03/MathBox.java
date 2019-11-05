package part1.lession03.task03;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author KhafizovAR
 */
public class MathBox extends ObjectBox {

    public MathBox(Number[] number) {
        objectCollection = new HashSet<Object>(Arrays.asList(number));
        objectCollection.remove(null);
    }

    /**
     * Возвращает сумму всех элементов
     * @return сумма элементов
     */
    public Number summator() {
        double summ = 0;
        for (Object n: this.objectCollection) {
            summ += ((Number)n).doubleValue();
        }
        return summ;
    }

    /**
     * Делим каждый элемент на указанное число, реузльтат сохраняется в основной коллекции
     * @param num делитель
     */
    public void splitter(double num) {
        Set<Object> rez = new HashSet<>();
        for (Object n: this.objectCollection) {
            rez.add(((Number)n).doubleValue() / num);
        }
        this.objectCollection = rez;
    }

    /**
     * Удаление объекта типа {@link Integer}, если он есть
     * @param num   удаляемый объект
     * @return
     */
    public boolean removeInt(Integer num) {
        return this.objectCollection.remove(num);
    }

    /**
     * Добавление объекта в коллекцию
     * @param obj  Добавляемый объект
     */
    @Override
    public void addBox(Object obj) {
        if(obj instanceof Number) {
            this.objectCollection.add((Number)obj);
        } else {
            throw new IllegalArgumentException("obj must be instance of Number");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((objectCollection == null) ? 0 : objectCollection.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MathBox [objectCollection=" + objectCollection + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MathBox other = (MathBox) obj;
        if (objectCollection == null) {
            if (other.objectCollection != null)
                return false;
        } else if (!objectCollection.equals(other.objectCollection))
            return false;
        return true;
    }

}
