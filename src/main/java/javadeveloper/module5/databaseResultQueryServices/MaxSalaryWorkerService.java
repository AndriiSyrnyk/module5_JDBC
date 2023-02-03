package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.MaxSalaryWorker;
import javadeveloper.module5.storage.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaxSalaryWorkerService {
    private Database database;
    private String sqlQuery = "SELECT name, salary FROM worker WHERE salary = ?";
    public MaxSalaryWorkerService(Database db)  {
        database = db;
    }
    public List<MaxSalaryWorker> findMaxSalaryWorkers() {
        List<MaxSalaryWorker> maxSalaryWorkerList = new ArrayList<>();

        try(Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, findMaxSalary());
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    maxSalaryWorkerList.add(new MaxSalaryWorker(
                            resultSet.getString("name"),
                            resultSet.getInt("salary")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maxSalaryWorkerList;
    }
    private int findMaxSalary() {
        String sql = "SELECT MAX(salary) AS max_salary FROM worker";

        try(Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt("max_salary");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}