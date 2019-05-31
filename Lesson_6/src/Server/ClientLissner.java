package Server;

import Client.sample.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class ClientLissner {
    DataOutputStream outputStream;
    DataInputStream inputStream;
    Socket socket;
    ChatServer chatServer;
    String nick;
    String wispNick;
    int timeOutMilliSec = 5000;
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
                                    chatServer.subscribe(ClientLissner.this);
                                    break;
                                } else {
                                    sendMsg("Неверный логин/пароль!");
                                }
                            }
                        }

                        while (true) {
                            String str = inputStream.readUTF();
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
                                    chatServer.sendWisper(msg, wispNick,ClientLissner.this);

                                } else {
                                    sendMsg("Нет такого пользователя");
                                }
                            } else if(str.startsWith("/blacklist ")) {
                                    String[] tokens = str.split(" ");
                                    blackList.add(tokens[1]);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                            }
                            else chatServer.broadcastMsg(ClientLissner.this,nick + " : " + str);

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
                    }
                }
            });t.start();
            if (outputStream == null){
            }else {
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
