package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.LongestProject;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LongestProjectService {
    private PreparedStatement selectByLongestProject;

    public LongestProjectService(Database database) throws SQLException {
        Connection connection = database.getConnection();
        selectByLongestProject = connection.prepareStatement(
                "SELECT CONCAT('Project', id) as name, DATEDIFF(month, start_date, finish_date) AS month_count " +
                        "FROM (" +
                        "  SELECT id, start_date, finish_date " +
                        "  FROM project " +
                        ") " +
                        "WHERE DATEDIFF(month, start_date, finish_date)= ?");
    }

    public List<LongestProject> findLongestProjects() {
        List<LongestProject> longestProjectsList = new ArrayList<>();

        try {
            selectByLongestProject.setInt(1, findLongestProjectDuration());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try(ResultSet resultSet = selectByLongestProject.executeQuery()) {
            while (resultSet.next()) {
                longestProjectsList.add(new LongestProject(
                        resultSet.getString("name"),
                        resultSet.getInt("month_count")
                ));
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

        try(ResultSet resultSet = Database.getInstance().executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt("month_count");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
