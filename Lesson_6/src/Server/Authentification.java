package Server;

import java.sql.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import static Server.ChatServer.lis;

public class Authentification {
    static Vector<String> list = new Vector<>();
    static Iterator<String> iterator= list.iterator();
    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            // обращение к драйверу

            Class.forName("org.sqlite.JDBC");
            // установка подключения
            connection = DriverManager.getConnection("jdbc:sqlite:DBUsers.db");
            // создание Statement для возможности оправки запросов
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        // формирование запроса
        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'", login, pass);

        try {
            // оправка запроса и получение ответа
            ResultSet rs = stmt.executeQuery(sql);

            // если есть строка возвращаем результат если нет то вернеться null
            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getNick() {
        // формирование запроса
        String sql = String.format("SELECT nickname FROM main");

        try {
            // оправка запроса и получение ответа
            ResultSet rs = stmt.executeQuery(sql);

            // если есть строка возвращаем результат если нет то вернеться null
            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void disconnect() {
        try {
            // закрываем соединение
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void saveHistory(String nick, String msg){
        String sql = String.format("INSERT INTO History (nickname, message) VALUES ('%s', '%s') ", nick, msg);
        try {
            int rs = stmt.executeUpdate(sql);
            System.out.println(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getHistory(){

        String sql = String.format("SELECT * FROM History");

        try {
            ResultSet rs = stmt.executeQuery(sql);
            lis.clear();
            while (rs.next()) {

                lis.add(rs.getString(1) +" : "+ rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String checkHistory(){
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            return iterator.next();

        }return null;
    }
}
