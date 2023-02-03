package javadeveloper.module5.storage;

import java.sql.*;

public class Database {
    private static final Database INSTANCE = new Database();
    private Connection connection;
    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        String connectionUrl = "jdbc:h2:./megasoft";
        try {
            connection = DriverManager.getConnection(connectionUrl, "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}