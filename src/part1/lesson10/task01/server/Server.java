package part1.lesson10.task01.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author KhafizovAR on 21.11.2019.
 * @project Stc20JavaMiddle
 */
public class Server<T extends ClientInstance> {

    public static final Integer SERVER_PORT = 4999;
    public static final Integer BROADCAST_PORT = 6226;
    public static final String INET_ADDR_NAME = "localhost";

    //Список клиентов
    protected List<T> connectedClients = Collections.synchronizedList(new ArrayList<>());

    public Server() {
        this.connectedClients = connectedClients;
    }

    /**
     * Метод запуска сервера
     * @throws IOException
     */
    public void runServer(Class<T> typeParameterClass) throws IOException {
        System.out.println("Запуск сервера на порту: " + SERVER_PORT);
        ServerSocket ss  = new ServerSocket(SERVER_PORT);
        while(true) {
            Socket s = null;
            try {
                s = ss.accept();
                System.out.println("Новый клиент : " + s + " \n.Создание потока для нового клиента.");
                //T t = (T) ClientInstance.getInstance(s, new DataInputStream(s.getInputStream()), new DataOutputStream(s.getOutputStream()), this);
                Constructor<?> ctor = typeParameterClass.getConstructor(s.getClass(),DataInputStream.class, DataOutputStream.class, Server.class);
                T t = (T) ctor.newInstance(new Object[] { s, new DataInputStream(s.getInputStream()), new DataOutputStream(s.getOutputStream()), this });
                this.connectedClients.add(t);
                t.start();
            } catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }

    /**
     * Широковещательная рассылка по UDP
     * @param message  текст сообщения
     */
    public synchronized void broadCastMessage(String message) {
        /* UDP socket */
        try {
            byte[] data = message.getBytes();
            InetAddress addr = InetAddress.getByName(INET_ADDR_NAME);
            DatagramPacket pack = new DatagramPacket(data, data.length, addr, BROADCAST_PORT);
            DatagramSocket ds = new DatagramSocket();
            ds.send(pack);
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получение списка подключенных клиентов
     * @return
     */
    public synchronized List<T> getConnectedClients() {
        return new ArrayList<T>(connectedClients);
    }

    /**
     * Удаление закрытого клиента
     * @param ci  Клиент
     * @return
     */
    public boolean clientClosed(T ci) {
        return connectedClients.remove(ci);
    }
}
