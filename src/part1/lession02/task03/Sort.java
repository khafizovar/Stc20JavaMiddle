package part1.lession02.task03;

/**
 * Интерфейс сортировки объектов
 * @author KhafizovAR
 */
public interface Sort {
    /**
     * Метод сортировки объектов
     * @param persons  массив объектов Person @see {@link Person}
     * @return  отсортированный массив объектов Person @see {@link Person}
     */
    Person[] sort(Person[] persons);
}
