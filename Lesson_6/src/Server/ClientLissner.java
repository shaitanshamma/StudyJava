package Server;

import Client.sample.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.*;

import static Server.Authentification.iterator;
import static Server.Authentification.list;
import static Server.ChatServer.lis;


public class ClientLissner {

    DataOutputStream outputStream;
    DataInputStream inputStream;
    Socket socket;
    ChatServer chatServer;
    String nick;
    String wispNick;
    int timeOutMilliSec = 50000;
    ArrayList<String> blackList;

    public String getNick() {
        return nick;
    }

    public ClientLissner(Socket socket, ChatServer chatServer) {


        try {
            this.socket = socket;
            this.chatServer = chatServer;
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            this.blackList = new ArrayList<>();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        // цикл для авторизации
                        while (true) {
                            String str = inputStream.readUTF();
                            // если сообщение начинается с /auth
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                // Вытаскиваем данные из БД
                                String newNick = Authentification.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null && !chatServer.isIn(newNick)) {
                                    // отправляем сообщение об успешной авторизации
                                    sendMsg("/authok");
                                    nick = newNick;
                                    //Authentification.getHistory();
                                    chatServer.subscribe(ClientLissner.this);
                                    ChatServer.log.info("подключился " + ClientLissner.this.nick);
                                    for (int i = 0; i < lis.size(); i++) {
                                        if (lis.get(i) != null) {
                                            sendMsg(lis.get(i));

                                        } else chatServer.broadcastMsg(ClientLissner.this, "чисто");
                                    }

                                    //  chatServer.broadcastMsg(ClientLissner.this,Authentification.checkHistory());

                                    break;

                                } else {
                                    sendMsg("Неверный логин/пароль!");
                                    ChatServer.log.info("Неверный логин/пароль! " + ClientLissner.this.nick);
                                }
                            }
                        }

                        while (true) {
                            String str = inputStream.readUTF();
                            ChatServer.log.error("dissconnect");
                            if (str.equals("/end")) {
                                outputStream.writeUTF("/serverClosed");
                                break;
                            } else if (str.startsWith("/w")) {
                                String[] tokens = str.split(" ", 3);
                                String wisp = tokens[1];
                                String msg = nick + " : " + tokens[2];
                                wispNick = wisp;
                                System.out.println(wispNick);
                                if (wispNick != null) {
                                    chatServer.sendWisper(msg, wispNick, ClientLissner.this);
                                    ChatServer.log.info("wisper");
                                } else {
                                    sendMsg("Нет такого пользователя");
                                }
                            } else if (str.startsWith("/blacklist ")) {
                                String[] tokens = str.split(" ");
                                blackList.add(tokens[1]);
                                sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                            } else {
                                chatServer.broadcastMsg(ClientLissner.this, nick + " : " + str);
                                Authentification.saveHistory(nick, str);

                            }

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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        chatServer.unsubscribe(ClientLissner.this);
                        ChatServer.log.info("отключение " + ClientLissner.this.nick);
                    }
                }
            });
            t.start();
            if (outputStream == null) {
                ChatServer.log.info("timeout");
            } else {
                socket.setSoTimeout(timeOutMilliSec);


            }
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

    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }


}
