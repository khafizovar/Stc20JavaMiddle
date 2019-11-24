package part1.lesson10;

import part1.lesson10.task01.server.ClientInstance;
import part1.lesson10.task01.server.Server;
import part1.lesson10.task02.server.ClientInstanceModified;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

/**
 * Фабрика классов клиентских потоков
 * @author KhafizovR by 22.11.2019
 * Stc20JavaMiddle
 */
public class ClientInstancePseudoFabric {
    /**
     * Получить экземпляр потока
     * @param version   версия/идентификатор
     * @param socket    экземляр сокета на котором будет виесть клиент
     * @param server    ссылка на объект сервера
     * @return  новый экземпляр клиента
     * @throws IOException
     */
    public static Optional<Thread> getClientThread(String version, Socket socket, Server server) throws IOException {
        switch(version) {
            case "first": {
                return Optional.of(new ClientInstance(socket, server));
            }
            case "second": {
                return Optional.of(new ClientInstanceModified(socket, server));
            }
            default: {
                return Optional.empty();
            }
        }
    }
}
