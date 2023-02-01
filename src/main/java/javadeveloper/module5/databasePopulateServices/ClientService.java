package javadeveloper.module5.databasePopulateServices;

import javadeveloper.module5.databaseTableClasses.Client;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private PreparedStatement insertSt;
    private Connection connection;

    public ClientService(Database database) throws SQLException {
        connection = database.getConnection();
        insertSt = connection.prepareStatement(
                "INSERT INTO client (id, name) VALUES(?, ?)"
        );
    }

    public void insertNewClients(List<Client> clientList) {
        try {
            for (Client client : clientList) {
                insertSt.setInt(1, client.getId());
                insertSt.setString(2, client.getName());
                insertSt.addBatch();
            }
            insertSt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { insertSt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
