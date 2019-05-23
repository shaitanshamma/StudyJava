package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientLissner {
    DataOutputStream outputStream;
    DataInputStream inputStream;
    Socket socket;
    ChatServer chatServer;

    public ClientLissner(Socket socket, ChatServer chatServer) {
        try {
            this.socket = socket;
            this.chatServer = chatServer;
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = inputStream.readUTF();
                            if (str.equals("/end")) {
                                break;
                            }
                            chatServer.broadcastMsg("Client: " + str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                            System.out.println("off");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            outputStream.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
