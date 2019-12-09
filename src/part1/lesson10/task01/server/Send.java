package part1.lesson10.task01.server;

import java.io.IOException;

/**
 * @author KhafizovAR on 09.12.2019.
 * @project Stc20JavaMiddle
 */
public interface Send {
    public void sendMessage(String message, Server server, ClientInstance client) throws IOException;
}
