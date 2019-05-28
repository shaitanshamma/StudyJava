package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

public class ChatServer {
    private Vector<ClientLissner> clients;

    public ChatServer() {
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();

        try {
            server = new ServerSocket(8989);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                clients.add(new ClientLissner(socket, this));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(String msg) {
        for (ClientLissner o : clients) {
            o.sendMsg(msg);
        }
    }
}