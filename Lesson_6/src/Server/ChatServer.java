package Server;

import org.apache.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.*;


public class ChatServer {
    //    static final Logger log = Logger.getLogger("");
//    static Handler handler;
//
//    static {
//        try {
//            handler = new FileHandler("1.log", true);
//            handler.setLevel(Level.ALL);
//            handler.setFormatter(new SimpleFormatter());
//            log.addHandler(handler);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    static Logger log = Logger.getLogger(ChatServer.class);

    private Vector<ClientLissner> clients;
    DataOutputStream outputStream;
    static Vector<String> lis = new Vector<>();

    public ChatServer() throws SQLException, IOException {
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();

        try {
            Authentification.connect();

            server = new ServerSocket(8989);
            System.out.println(lis);
            //System.out.println("Сервер запущен");
            log.info("Сервер запущен");

            while (true) {
                socket = server.accept();
                // System.out.println("Клиент подключился");
                new ClientLissner(socket, this);
                log.info("Клиент подключился");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                log.info("Сокет закрыт");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
                log.info("Сервер аут");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Authentification.disconnect();
            log.info("Сервер аут");
        }
    }

    // подписываем клиента на рассылку
    public void subscribe(ClientLissner client) {
        clients.add(client);
        Authentification.getHistory();
        //System.out.println(lis);
        // lis.clear();
    }

    // отписываем клиента от рассылки сообщений
    public void unsubscribe(ClientLissner client) {
        clients.remove(client);
    }


    public void broadcastMsg(ClientLissner nick, String msg) {
        for (ClientLissner o : clients) {
            if (!o.checkBlackList(nick.getNick())) {
                o.sendMsg(msg);
            }
        }
    }

    public void sendWisper(String msg, String wispNick, ClientLissner whoSend) {
        for (ClientLissner o : clients) {
            if (o.getNick().equals(wispNick) && !o.checkBlackList(whoSend.getNick())) {
                o.sendMsg(msg);
                whoSend.sendMsg("to " + wispNick + ": " + msg);
            } else if (o.getNick().equals(wispNick) && o.checkBlackList(whoSend.getNick())) {
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

    public void sendHistoryToClient(String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


