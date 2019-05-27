package Server;

import Client.sample.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Client.sample.Controller.isAuthorized;

public class ClientLissner {
    DataOutputStream outputStream;
    DataInputStream inputStream;
    Socket socket;
    ChatServer chatServer;
    String nick;
    String wispNick;


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
                        // цикл для авторизации
                        while (true) {
                            String str = inputStream.readUTF();
                            // если сообщение начинается с /auth
                            if(str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                // Вытаскиваем данные из БД
                                String newNick = Authentification.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null && !chatServer.isIn(newNick)) {
                                    // отправляем сообщение об успешной авторизации
                                    sendMsg("/authok");
                                    nick = newNick;
                                    chatServer.subscribe(ClientLissner.this);
                                    break;
                                } else {
                                    sendMsg("Уже есть такой");

                                    //sendMsg("Неверный логин/пароль!");
                                }
                            }
                        }

                        // блок для отправки сообщений
                        while (true) {
                            String str = inputStream.readUTF();
                            if(str.equals("/end")) {
                                outputStream.writeUTF("/serverClosed");
                                break;
                            }
                            chatServer.broadcastMsg(nick + " : " + str);

                        }
                        while(true){
                            String wisp= inputStream.readUTF();
                            if(wisp.startsWith("/w")) {
                                String[] tokens = wisp.split(" ",2);
                                // Вытаскиваем данные из БД
                                String newNick = Authentification.getNickByLoginAndPass(tokens[1], tokens[2]);
                                String msg = tokens[2];
                                wispNick = tokens[1];
                                System.out.println(wispNick);
                                if (wispNick != null) {
                                    // отправляем сообщение об успешной авторизации
                                    chatServer.sendWisper(msg, newNick);

                                } else {
                                    sendMsg("Нет такого пользователя");
                                }
                            }
                            //chatServer.sendWisper(str);
                        }
                    }  catch (IOException e) {
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        chatServer.unsubscribe(ClientLissner.this);
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
