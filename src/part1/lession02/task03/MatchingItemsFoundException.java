package part1.lession02.task03;

/**
 * Класс исключения найденных одинаковых элементов
 * @author KhafizovAR
 */
public class MatchingItemsFoundException  extends Exception {
    public MatchingItemsFoundException() {}
    public MatchingItemsFoundException(String message) {
        super(message);
    }
}