package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Vector;

public class ChatServer {
    private Vector<ClientLissner> clients;


    public ChatServer() throws SQLException {
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();

        try {
            Authentification.connect();

            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientLissner(socket, this);
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
            Authentification.disconnect();
        }
    }

    // подписываем клиента на рассылку
    public void subscribe(ClientLissner client) {
        clients.add(client);
    }

    // отписываем клиента от рассылки сообщений
    public void unsubscribe(ClientLissner client) {
        clients.remove(client);
    }


    public void broadcastMsg(ClientLissner nick,String msg) {
        for (ClientLissner o: clients) {
            if(!o.checkBlackList(nick.getNick())) {
                o.sendMsg(msg);
            }
        }
    }

    public void sendWisper(String msg, String wispNick ,ClientLissner whoSend) {
        for (ClientLissner o : clients) {
            if (o.getNick().equals(wispNick) && !o.checkBlackList(whoSend.getNick())) {
                o.sendMsg(msg);
                whoSend.sendMsg("to " + wispNick + ": " + msg);
            }else if (o.getNick().equals(wispNick) && o.checkBlackList(whoSend.getNick())) {
                whoSend.sendMsg("Вы в черном списке у " + o.getNick());
            }
        }
    }

    public boolean isIn(String nick) {
        for (ClientLissner o : clients) {
            if (o.nick.equals(nick)) {
                return true;
            }
        }
        return false;
    }
}
