package part1.lession02.task03;

import java.util.*;

/**
 * Реализация второго класса сортировки
 */
public class SecondSort implements Sort {
    /**
     * Оригинальный массив не изменяется
     * @param persons  массив объектов Person @see {@link Person}
     * @return отсортированный массив объектов Person @see {@link Person}
     */
    @Override
    public Person[] sort(Person[] persons) {
        List<Person> nl = new ArrayList<>(Arrays.asList(persons));
        Collections.sort(nl, new Comparator<Person>() {
            public int compare(Person o1, Person o2) {
                int sComp = o1.getSex().compareTo(o2.getSex());
                if(sComp != 0) {
                    return sComp;
                } else {
                    sComp = Integer.compare(o1.getAge(), o2.getAge());
                    if(sComp != 0) {
                        return sComp;
                    } else {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
            }
        });
        return nl.toArray(new Person[0]);
    }
}
