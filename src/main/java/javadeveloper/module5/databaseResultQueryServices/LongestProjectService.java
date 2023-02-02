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
    private Connection connection;
    private String sql = "SELECT CONCAT('Project', id) as name, DATEDIFF(month, start_date, finish_date) AS month_count " +
            "FROM (" +
            "  SELECT id, start_date, finish_date " +
            "  FROM project " +
            ") " +
            "WHERE DATEDIFF(month, start_date, finish_date)= ?";
    public LongestProjectService(Database database) {
       connection = database.getConnection();
    }
    public List<LongestProject> findLongestProjects() {
        List<LongestProject> longestProjectsList = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            selectByLongestProject = connection.prepareStatement(sql);
            selectByLongestProject.setInt(1, findLongestProjectDuration());
            resultSet = selectByLongestProject.executeQuery();
            while (resultSet.next()) {
                longestProjectsList.add(new LongestProject(
                        resultSet.getString("name"),
                        resultSet.getInt("month_count")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { selectByLongestProject.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (resultSet != null) { try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); } }
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
