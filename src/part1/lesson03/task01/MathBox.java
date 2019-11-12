package part1.lesson03.task01;

import java.util.*;

/**
 * Задание 1. Написать класс MathBox, реализующий следующий функционал:
 *
 * Конструктор на вход получает массив Number. Элементы не могут повторяться. Элементы массива внутри объекта раскладываются в подходящую коллекцию (выбрать самостоятельно).
 * Существует метод summator, возвращающий сумму всех элементов коллекции.
 * Существует метод splitter, выполняющий поочередное деление всех хранящихся в объекте элементов на делитель, являющийся аргументом метода. Хранящиеся в объекте данные полностью заменяются результатами деления.
 * Необходимо правильно переопределить методы toString, hashCode, equals, чтобы можно было использовать MathBox для вывода данных на экран и хранение объектов этого класса в коллекциях (например, hashMap). Выполнение контракта обязательно!
 * Создать метод, который получает на вход Integer и если такое значение есть в коллекции, удаляет его.
 * 
 * @author KhafizovAR
 */
public class MathBox {
    Set<Number> numbers;

    public MathBox(Number[] number) {
        numbers = new HashSet<Number>(Arrays.asList(number));
    }

    /**
     * Возвращает сумму всех элементов
     * @return сумма элементов
     */
    public Number summator() {
        double sum = 0;
        for (Number n: this.numbers) {
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
        if(this.numbers.contains(null)) {
            rez.add(null);
            this.numbers.remove(null);
        }
        for (Number n: this.numbers) {
                rez.add(n.doubleValue() / num);
        }
        this.numbers = rez;
    }

    /**
     * Удаление объекта типа {@link Integer}, если он есть
     * @param num   удаляемый объект
     * @return
     */
    public boolean removeInt(Integer num) {
        return this.numbers.remove(num);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numbers == null) ? 0 : numbers.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MathBox [numbers=" + numbers + "]";
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
        if (numbers == null) {
            if (other.numbers != null)
                return false;
        } else if (!numbers.equals(other.numbers))
            return false;
        return true;
    }

}
