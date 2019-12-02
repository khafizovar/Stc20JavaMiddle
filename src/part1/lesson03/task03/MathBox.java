package part1.lesson03.task03;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author KhafizovAR
 */
public class MathBox extends ObjectBox<Number> {

    public MathBox(Number[] number) {
        objectCollection = new HashSet<Number>(Arrays.asList(number));
    }

    /**
     * Возвращает сумму всех элементов
     * @return сумма элементов
     */
    public Number summator() {
        double sum = 0;
        for (Number n: this.objectCollection) {
            if(n != null)
                sum += n.doubleValue();
        }
        return sum;
    }

    /**
     * Делим каждый элемент на указанное число, реузльтат сохраняется в основной коллекции
     * @param num делитель
     */
    public void splitter(double num) {
        Set<Number> rez = new HashSet<>();
        if(this.objectCollection.contains(null)) {
            rez.add(null);
            this.objectCollection.remove(null);
        }
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

}
