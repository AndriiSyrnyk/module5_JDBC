package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.YoungestEldestWorker;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class YoungestEldestWorkerService {
    private PreparedStatement selectYoungestAndEldestWorker;

    public YoungestEldestWorkerService(Database database) throws SQLException {
        Connection connection = database.getConnection();
        selectYoungestAndEldestWorker = connection.prepareStatement(
                "SELECT 'YOUNGEST' AS type, name, birthday " +
                        "FROM worker " +
                        "WHERE birthday = ? " +
                        "UNION " +
                        "SELECT 'ELDEST' AS type, name, birthday " +
                        "FROM worker " +
                        "WHERE birthday = ?");
    }
    public List<YoungestEldestWorker> findYoungestEldestWorker() {
        List<YoungestEldestWorker> youngestEldestWorkerList = new ArrayList<>();

        try {
            selectYoungestAndEldestWorker.setString(1, findYoungestWorker());
            selectYoungestAndEldestWorker.setString(2, findEldestWorker());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try(ResultSet resultSet = selectYoungestAndEldestWorker.executeQuery()) {
            while (resultSet.next()) {
                youngestEldestWorkerList.add(new YoungestEldestWorker(
                        resultSet.getString("type"),
                        resultSet.getString("name"),
                        resultSet.getString("birthday")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return youngestEldestWorkerList;
    }

    private String findYoungestWorker() {
        String sql = "SELECT MAX(birthday) as max_birthday FROM worker";

        try(ResultSet resultSet = Database.getInstance().executeQuery(sql)) {
            resultSet.next();
            return resultSet.getString("max_birthday");
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String findEldestWorker() {
        String sql = "SELECT MIN(birthday) as min_birthday FROM worker";

        try(ResultSet resultSet = Database.getInstance().executeQuery(sql)) {
            resultSet.next();
            return resultSet.getString("min_birthday");
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}
