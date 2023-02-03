package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.MaxProjectCountClient;
import javadeveloper.module5.storage.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class MaxProjectCountClientService {
    private Database database;
    private String sqlQuery = "SELECT name, projects_count " +
            "FROM (" +
            "SELECT client.name AS name, COUNT(project.client_id) AS projects_count, " +
            "FROM client " +
            "INNER JOIN project " +
            "ON client.ID = project.client_id " +
            "GROUP BY project.client_id " +
            ")" +
            "WHERE projects_count = ?";
    public MaxProjectCountClientService(Database db) {
        database = db;
    }
    public List<MaxProjectCountClient> findMaxProjectCountClients() {
        List<MaxProjectCountClient> maxProjectCountClientList = new ArrayList<>();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, findMaxProjectCount());
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    maxProjectCountClientList.add(new MaxProjectCountClient(
                            resultSet.getString("name"),
                            resultSet.getInt("projects_count")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        try (Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt("max_project_count");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}