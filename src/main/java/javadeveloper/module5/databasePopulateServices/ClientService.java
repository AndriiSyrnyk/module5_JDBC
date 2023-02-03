package javadeveloper.module5.databasePopulateServices;

import javadeveloper.module5.databaseTableClasses.Client;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
public class ClientService {
    private Database database;
    private String sqlQuery = "INSERT INTO client (id, name) VALUES(?, ?)";
    public ClientService(Database db) {
        database = db;
    }
    public void insertNewClients(List<Client> clientList) {
        try (Connection connection = database.getConnection();
             PreparedStatement insertSt = connection.prepareStatement(sqlQuery)) {
            for (Client client : clientList) {
                insertSt.setInt(1, client.getId());
                insertSt.setString(2, client.getName());
                insertSt.addBatch();
            }
            insertSt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
