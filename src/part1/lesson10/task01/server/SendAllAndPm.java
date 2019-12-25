package part1.lesson10.task01.server;

import part1.lesson10.task01.server.ClientInstance;
import part1.lesson10.task01.server.Send;
import part1.lesson10.task01.server.Server;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author KhafizovAR on 09.12.2019.
 * @project Stc20JavaMiddle
 */
public class SendAllAndPm implements Send {

    protected DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

    @Override
    public void sendMessage(String message, Server server, ClientInstance client) throws IOException {
        if(message.startsWith("#") && !message.equals("#name")) {
            StringTokenizer st = new StringTokenizer(message, "# ");
            String name = st.nextToken();
            for (Object mc : server.getConnectedClients()) {
                if (((ClientInstance) mc).getClientName().equals(name)) {
                    if(st.hasMoreTokens()) {
                        String mess = message.replace("#" + name, "");
                        ((ClientInstance) mc).dos.writeUTF("[" + client.getClientName()  + "] - [" + timeFormat.format(new Date()) + "] [Private Message]: " + mess);
                    }
                    return;
                }
            }
        } else {
            server.broadCastMessage("[" + client.getClientName() + "] - [" + timeFormat.format(new Date()) + "]: " + message);
        }
    }
}
