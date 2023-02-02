package javadeveloper.module5.storage;

import java.sql.*;

public class Database {
    private static final Database INSTANCE = new Database();
    private Connection connection;
    private Database()  {
        getConnection();
    }
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
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int executeUpdate(String sql) {
        try (Statement st = connection.createStatement()){
            return st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet executeQuery(String sql) {
        try {
            Statement st = connection.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}