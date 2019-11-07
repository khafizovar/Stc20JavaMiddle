package part1.lession02.task03;

import java.util.*;

/**
 * Реализация второго класса сортировки
 * @author KhafizovAR
 */
public class BubbleSort implements Sort {
    /**
     * Оригинальный массив не изменяется Тип сортировки: Пузырьковая сортировка
     * @param persons  массив объектов Person @see {@link Person}
     * @return отсортированный массив объектов Person @see {@link Person}
     */
    @Override
    public Person[] sort(Person[] persons) throws Exception {
        Person[] p = Arrays.copyOf(persons,persons.length);
        this.bubbleSort(p);
        return p;
    }

    /**
     * Сортировка пузырьком объектов {@link Person}
     * @param p Исходный массив для сортировки
     */
    private void bubbleSort(Person[] p) throws Exception {
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < p.length; i++) {
                if(p[i].getAge() == p[i - 1].getAge() &&
                        p[i].getName().equals(p[i - 1].getName())) {
                    // зачем нужно выбрасывать именно такой исключение?
                    throw new MatchingItemsFoundException("Найдены элементы с одинаковым именем и возрастом");
                }

                if (p[i].getSex().compareTo(p[i - 1].getSex()) < 0) {               //по первому полю
                    swap(p, i, i - 1);
                    needIteration = true;
                } else if( p[i].getSex().compareTo(p[i - 1].getSex()) == 0) {
                    if (p[i].getAge() > p[i - 1].getAge()) {                         //по второму полю
                        swap(p, i, i - 1);
                        needIteration = true;
                    } else if(p[i].getAge() == p[i - 1].getAge()) {
                        if (p[i].getName().compareTo(p[i - 1].getName()) < 0) {     //по третьему полю
                            swap(p, i, i - 1);
                            needIteration = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Обмен местами элементов массива, принимает в качестве параметров индексы элементов
     * @param array массив
     * @param ind1  индекс элемента 1
     * @param ind2  индекс элемента 2
     */
    private void swap(Person[] array, int ind1, int ind2) {
        Person tmp = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = tmp;
    }
}
