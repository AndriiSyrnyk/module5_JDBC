package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.YoungestEldestWorker;
import javadeveloper.module5.storage.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class YoungestEldestWorkerService {
    private Database database;
    private String sqlQuery = "SELECT 'YOUNGEST' AS type, name, birthday " +
            "FROM worker " +
            "WHERE birthday = ? " +
            "UNION " +
            "SELECT 'ELDEST' AS type, name, birthday " +
            "FROM worker " +
            "WHERE birthday = ?";
    public YoungestEldestWorkerService(Database db) {
        database = db;
    }
    public List<YoungestEldestWorker> findYoungestEldestWorker() {
        List<YoungestEldestWorker> youngestEldestWorkerList = new ArrayList<>();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, findYoungestWorker());
            statement.setString(2, findEldestWorker());
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    youngestEldestWorkerList.add(new YoungestEldestWorker(
                            resultSet.getString("type"),
                            resultSet.getString("name"),
                            resultSet.getString("birthday")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return youngestEldestWorkerList;
    }

    private String findYoungestWorker() {
        String sql = "SELECT MAX(birthday) as max_birthday FROM worker";

        try(Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getString("max_birthday");
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
    private String findEldestWorker() {
        String sql = "SELECT MIN(birthday) as min_birthday FROM worker";

        try(Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getString("min_birthday");
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
