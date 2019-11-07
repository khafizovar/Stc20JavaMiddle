package part1.lession02.task03;

/**
 * Класс исключения найденных одинаковых элементов
 * @author KhafizovAR
 */
public class DupleByAgeAndNameFoundException extends Exception {
    public DupleByAgeAndNameFoundException(String message) {
        super(message);
    }
}