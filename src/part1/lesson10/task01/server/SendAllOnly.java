package part1.lesson10.task01.server;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author KhafizovAR on 09.12.2019.
 * @project Stc20JavaMiddle
 */
public class SendAllOnly implements Send {

    protected DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

    @Override
    public void sendMessage(String message, Server server, ClientInstance client) throws IOException {
        System.out.println("old send message");
        server.broadCastMessage("[" + client.clientName + "] - [" + timeFormat.format(new Date()) + "]: " + message);
    }
}
