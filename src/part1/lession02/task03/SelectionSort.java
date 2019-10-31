package part1.lession02.task03;

import java.util.Arrays;

/**
 * Реализация первого класса сортировки
 */
public class SelectionSort implements Sort {
    /**
     * Оригинальный массив не изменяется, тип сортировки: Сортировка выбором
     * @param persons  массив объектов Person @see {@link Person}
     * @return отсортированный массив объектов Person @see {@link Person}
     */
    @Override
    public Person[] sort(Person[] persons) {
        Person[] p = Arrays.copyOf(persons,persons.length);
        this.selectionSort(p);
        return p;
    }

    /**
     * Сортировка выбором
     * @param p
     */
    private void selectionSort (Person[] p) {
        for (int left = 0; left < p.length; left++) {
            int minInd = left;
            for (int i = left; i < p.length; i++) {
                if (p[i].getSex().compareTo(p[minInd].getSex()) < 0) {              //по первому полю
                    minInd = i;
                } else if( p[i].getSex().compareTo(p[minInd].getSex()) == 0) {
                    if (p[i].getAge() > p[minInd].getAge()) {                       //по второму полю
                        minInd = i;
                    } else if(p[i].getAge() == p[minInd].getAge()) {
                        if (p[i].getName().compareTo(p[minInd].getName()) < 0) {    //по третьему полю
                            minInd = i;
                        }
                    }
                }
            }
            swap(p, left, minInd);
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
