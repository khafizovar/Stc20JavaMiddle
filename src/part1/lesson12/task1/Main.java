package part1.lesson12.task1;

/**
 * Задание 1.     Необходимо создать программу, которая продемонстрирует утечку памяти в Java. При этом объекты должны не только создаваться,
 * но и периодически частично удаляться, чтобы GC имел возможность очищать часть памяти. Через некоторое время программа
 * должна завершиться с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 * @author KhafizovAR on 26.11.2019.
 * @project Stc20JavaMiddle
 */
public class Main {

    private static int n = 1;
    public static void main(String args[]) throws InterruptedException {
        while(true) {
            genereateString();
            Thread.currentThread().sleep(1000);
        }
    }

    public static void genereateString() {
        System.out.println("n:" + n + " Free Memory: " + Runtime.getRuntime().freeMemory());
        String str = "";
        for(int i = 1; i < n; i++) {
            str += str + "11";
        }
        n++;
    }
}
