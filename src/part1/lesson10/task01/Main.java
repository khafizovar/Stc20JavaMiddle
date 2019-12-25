package part1.lesson10.task01;



import java.io.IOException;

import part1.lesson10.task01.server.Server;

/**
 * ДЗ_10
 * Задание 1. Разработать приложение - многопользовательский чат, в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу. После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
 *
 * @author KhafizovAR on 21.11.2019.
 * @project Stc20JavaMiddle
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.runServer("default");
    }
}
