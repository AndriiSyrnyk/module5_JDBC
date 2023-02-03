package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.LongestProject;
import javadeveloper.module5.storage.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LongestProjectService {
    private Database database;
    private String sqlQuery = "SELECT CONCAT('Project', id) as name, DATEDIFF(month, start_date, finish_date) AS month_count " +
            "FROM (" +
            "  SELECT id, start_date, finish_date " +
            "  FROM project " +
            ") " +
            "WHERE DATEDIFF(month, start_date, finish_date)= ?";

    public LongestProjectService(Database db) {
       database = db;
    }
    public List<LongestProject> findLongestProjects() {
        List<LongestProject> longestProjectsList = new ArrayList<>();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, findLongestProjectDuration());

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    longestProjectsList.add(new LongestProject(
                            resultSet.getString("name"),
                            resultSet.getInt("month_count"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return longestProjectsList;
    }
    private int findLongestProjectDuration() {
        String sql = "SELECT MAX(month_count) AS month_count " +
                "FROM (" +
                "SELECT id AS project_id, DATEDIFF(month, start_date, finish_date) AS month_count " +
                "FROM (" +
                "SELECT id, start_date, finish_date " +
                "FROM project" +
                "))";

        try (Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt("month_count");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}