package part1.lesson10.task02.server;

import part1.lesson10.task01.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author KhafizovAR on 21.11.2019.
 * @project Stc20JavaMiddle
 */
public class ClientInstanceModified extends part1.lesson10.task01.server.ClientInstance {

    public ClientInstanceModified(Socket s, Server serv) throws IOException {
        super(s, serv);
    }

    /**
     * Массовая рассылка
     * @param message
     * @throws IOException
     */
    @Override
    protected void sendMessage(String message) throws IOException {
        if(message.startsWith("#") && !message.equals("#name")) {
            StringTokenizer st = new StringTokenizer(message, "# ");
            String name = st.nextToken();
            for (Object mc : this.server.getConnectedClients()) {
                if (((ClientInstanceModified) mc).getClientName().equals(name)) {
                    if(st.hasMoreTokens()) {
                        String mess = message.replace("#" + name, "");
                        ((ClientInstanceModified) mc).dos.writeUTF("[" + this.clientName + "] - [" + timeFormat.format(new Date()) + "] [Private Message]: " + mess);
                    }
                     return;
                }
            }
        } else {
            this.server.broadCastMessage("[" + this.clientName + "] - [" + timeFormat.format(new Date()) + "]: " + message);
        }
    }
}
