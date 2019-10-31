package part1.lession02.task03;

import java.util.Arrays;

/**
 * Реализация первого класса сортировки
 */
public class FirstSort implements Sort {
    /**
     * Оригинальный массив не изменяется
     * @param persons  массив объектов Person @see {@link Person}
     * @return отсортированный массив объектов Person @see {@link Person}
     */
    @Override
    public Person[] sort(Person[] persons) {
        Person[] p = Arrays.copyOf(persons,persons.length);
        this.quickSort(p, 0 , persons.length -1);
        return p;
    }

    private void quickSort (Person[] array, int l, int h) {
        if (array.length == 0)
            return;

        if (l >= h)
            return;

        int middle = l + (h - l) / 2;
        Person op = array[middle];

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = l, j = h;
        while (i <= j) {
            while (array[i].getAge() < op.getAge()) {
                i++;
            }

            while (array[j].getAge() > op.getAge()) {
                j--;
            }

            if (i <= j) {//меняем местами
                Person temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        // вызов рекурсии для сортировки левой и правой части
        if (l < j)
            quickSort(array, l, j);

        if (h > i)
            quickSort(array, i, h);
    }
}
