package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.ProjectPrices;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectPricesService {
    private Database database;
    private String sqlQuery = "SELECT CONCAT('Project', id_project) as name, SUM(duration_in_months * salary) AS price " +
            "FROM (" +
            "SELECT id_project, DATEDIFF(month, start_date, finish_date) AS duration_in_months, worker.salary AS salary " +
            "FROM (" +
            "SELECT id AS id_project, start_date, finish_date " +
            "FROM project) " +
            "INNER JOIN project_worker " +
            "ON id_project = project_worker.project_id " +
            "INNER JOIN worker " +
            "ON project_worker.worker_id = worker.ID) " +
            "GROUP BY id_project " +
            "ORDER BY price DESC";
    public ProjectPricesService(Database db) {
        database = db;
    }
    public List<ProjectPrices> printProjectPrices () {
        List<ProjectPrices> projectPricesList = new ArrayList<>();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    projectPricesList.add(new ProjectPrices(
                            resultSet.getString("name"),
                            resultSet.getInt("price")
                    ));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projectPricesList;
    }
}
