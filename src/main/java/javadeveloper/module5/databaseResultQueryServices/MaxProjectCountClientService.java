package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.MaxProjectCountClient;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MaxProjectCountClientService {
    private PreparedStatement selectByMaxProjectCountClient;
    private Connection connection;
    private String sql = "SELECT name, projects_count " +
            "FROM (" +
            "SELECT client.name AS name, COUNT(project.client_id) AS projects_count, " +
            "FROM client " +
            "INNER JOIN project " +
            "ON client.ID = project.client_id " +
            "GROUP BY project.client_id " +
            ")" +
            "WHERE projects_count = ?";

    public MaxProjectCountClientService(Database database) {
        connection = database.getConnection();
    }
    public List<MaxProjectCountClient> findMaxProjectCountClients() {
        List<MaxProjectCountClient> maxProjectCountClientList = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            selectByMaxProjectCountClient = connection.prepareStatement(sql);
            selectByMaxProjectCountClient.setInt(1, findMaxProjectCount());
            resultSet = selectByMaxProjectCountClient.executeQuery();
            while (resultSet.next()) {
                maxProjectCountClientList.add(new MaxProjectCountClient(
                        resultSet.getString("name"),
                        resultSet.getInt("projects_count")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { selectByMaxProjectCountClient.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (resultSet != null) { try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }

        return maxProjectCountClientList;
    }
    private int findMaxProjectCount() {
        String sql = "SELECT MAX (projects_count) AS max_project_count " +
                "FROM (" +
                "SELECT client.name AS name, COUNT(project.client_id) AS projects_count, " +
                "FROM client " +
                "INNER JOIN project " +
                "ON client.id = project.client_id " +
                "GROUP BY project.client_id " +
                ")";

        try(ResultSet resultSet = Database.getInstance().executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt("max_project_count");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}