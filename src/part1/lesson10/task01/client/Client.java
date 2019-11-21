package part1.lesson10.task01.client;

import part1.lesson10.task01.server.Server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author KhafizovAR on 21.11.2019.
 * @project Stc20JavaMiddle
 */
public class Client extends Thread {

    public static void main(String[] args) throws IOException {
        Client cl = new Client();
        cl.run();
    }


    @Override
    public void run() {
        System.out.println("Run client");
        try {
            Scanner scn = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName(Server.INET_ADDR_NAME);

            Socket s = new Socket(ip, Server.SERVER_PORT);

            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            System.out.println("Запуск слушателя сообщений");
            ClientReader clientReader = new ClientReader(s);
            clientReader.start();
            BroadCastClient brClient = new BroadCastClient();
            brClient.start();
            while (true) {
                String toSend = scn.nextLine();
                dos.writeUTF(toSend);

                if(toSend.equals("quit")) {
                    System.out.println("Закрытие соединения : " + s);
                    s.close();
                    System.out.println("Соединение закрыто");
                    break;
                }
            }
            scn.close();
            dos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Класс-поток прослушивает серверный канал на предмет новых сообщений
     */
    class ClientReader extends Thread {
        private final DataInputStream dis;
        private final Socket s;
        private boolean flag = true;

        public ClientReader(Socket s) throws IOException {
            this.dis = new DataInputStream(s.getInputStream());
            this.s = s;
        }

        @Override
        public void run() {
            try {
                while (!s.isClosed()) {
                    String s = dis.readUTF();
                    System.out.println(s);
                }
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class BroadCastClient extends Thread {

        private byte[] receive = new byte[65535];
        private DatagramSocket ds= new DatagramSocket(Server.BROADCAST_PORT);
        private DatagramPacket DpReceive = null;
        private boolean stopFlag = false;

        public BroadCastClient() throws SocketException {
            ds.setBroadcast(true);
        }

        @Override
        public void run() {
            try {
                while (!stopFlag) {
                    DpReceive = new DatagramPacket(receive, receive.length);
                    ds.receive(DpReceive);
                    System.out.println("Broadcast message:" + new String(receive, StandardCharsets.UTF_8));
                    receive = new byte[65535];
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        public boolean isStopFlag() {
            return stopFlag;
        }

        public void setStopFlag(boolean stopFlag) {
            this.stopFlag = stopFlag;
        }
    }





}
