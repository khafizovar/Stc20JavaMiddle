package part1.lession02.task01;

/**
 * Задание 1. Написать программу ”Hello, World!”. В ходе выполнения программы она должна выбросить исключение и завершиться с ошибкой.
 *
 * Смоделировав ошибку «NullPointerException»
 * Смоделировав ошибку «ArrayIndexOutOfBoundsException»
 * Вызвав свой вариант ошибки через оператор throw
 * @author KhafizovAR
 */
public class HelloWorld {
    public static void main(String[] args) throws Exception {

        //«NullPointerException»
        try {
            String s1 = null;
            System.out.println(s1.substring(0));
        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }
        // «ArrayIndexOutOfBoundsException»
        try {
            int[] s2 = {1, 2, 3, 4, 5};
            System.out.println(s2[5]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        //MyException
        throw new HelloWorld().new CustomException("My custom Exception");

    }

    class CustomException extends Exception {
        CustomException(String errorMessage) {
            super(errorMessage);
        }
    }
}
