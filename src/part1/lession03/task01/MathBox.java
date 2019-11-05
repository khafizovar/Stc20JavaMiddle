package part1.lession03.task01;

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
        numbers.remove(null);
    }

    /**
     * Возвращает сумму всех элементов
     * @return сумма элементов
     */
    public Number summator() {
        double summ = 0;
        for (Number n: this.numbers) {
            summ += n.doubleValue();
        }
        return summ;
    }

    /**
     * Делим каждый элемент на указанное число, реузльтат сохраняется в основной коллекции
     * @param num делитель
     */
    public void splitter(double num) {
        Set<Number> rez = new HashSet<>();
        for (Number n: this.numbers) {
            rez.add(n.doubleValue() / num);
        }
        this.numbers = rez;
    }

    public boolean removeInt(int num) {
        return this.numbers.remove(num);
    }

    @Override
    public String toString() {
        String s = "[";
        for (Number n : numbers) {
            s += n + "\t";
        }
        s += "]";
        //System.out.println(s);
        return s;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numbers == null) ? 0 : numbers.hashCode());
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
        MathBox other = (MathBox) obj;
        if (numbers == null) {
            if (other.numbers != null)
                return false;
        } else if (!numbers.equals(other.numbers))
            return false;
        return true;
    }

}
