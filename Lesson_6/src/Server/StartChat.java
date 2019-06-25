package Server;

import java.io.IOException;
import java.sql.SQLException;

public class StartChat {
    public static void main(String[] args) throws SQLException {
        try {
            new ChatServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
