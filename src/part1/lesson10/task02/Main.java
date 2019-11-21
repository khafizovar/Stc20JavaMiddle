package part1.lesson10.task02;



import part1.lesson10.task02.server.Server;

import java.io.IOException;

/**
 * @author KhafizovAR on 21.11.2019.
 * @project Stc20JavaMiddle
 */
public class Main {
    /**
     * Задание 2.  Усовершенствовать задание 1: *
     * a.      добавить возможность отправки личных сообщений (unicast). *
     * b.      добавить возможность выхода из чата с помощью написанной в чате команды «quit»
     * @author KhafizovAR on 21.11.2019.
     * @project Stc20JavaMiddle
     */
    public static void main(String[] args) throws IOException {
        Server.runServer();
    }
}
