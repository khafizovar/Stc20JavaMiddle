package part1.lesson10.task01.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author KhafizovAR on 21.11.2019.
 * @project Stc20JavaMiddle
 */
public class ClientInstance extends Thread {

    private DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;

    private String clientName;
    private boolean isRegistered = false;

    public ClientInstance(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    private void registration() throws IOException {
        String received;
        while(true) {
            this.dos.writeUTF("Для регистрации в чате наберите  #name Ваше_имя;");
            received = this.dis.readUTF();
            StringTokenizer st = new StringTokenizer(received, "# ");
            if (st.nextToken().equals("name")) {
                String newName = st.nextToken();
                this.isRegistered = true;
                for (ClientInstance mc : Server.getConnectedClients()) {
                    if(mc.getName().equals(newName)) {
                        dos.writeUTF("Клиент с таким именеме уже существует!");
                        this.isRegistered = false;
                        break;
                    }
                }
                if(this.isRegistered) {
                    this.clientName = newName;
                    this.broadcast("#Поприветствуем " + this.clientName);
                    this.dos.writeUTF("Вы зарегистрированы под именем:" + this.clientName);
                    return;
                }
            }
            dos.writeUTF("Команда не распознана:" + received);
        }
    }

    /**
     * Массовая рассылка
     * @param message
     * @throws IOException
     */
    private void broadcast(String message) throws IOException {
        Server.broadCastMessage("[" + this.clientName + "] - [" + timeFormat.format(new Date()) + "]: " + message);
    }


    @Override
    public void run() {
        String received;
        try {
            this.registration();
            dos.writeUTF("Вы в чате. моежете общаться (для выхода наберите 'quit')");
            while (true) {
                received = dis.readUTF();
                if (received.equalsIgnoreCase("quit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    this.broadcast("#Выходит из чата");
                    this.s.close();
                    System.out.println("Connection closed");
                    Server.clientClosed(this);
                    break;
                }
                this.broadcast(received);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }


        try {
            this.dis.close();
            this.dos.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
