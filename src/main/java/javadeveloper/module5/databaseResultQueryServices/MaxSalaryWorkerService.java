package javadeveloper.module5.databaseResultQueryServices;

import javadeveloper.module5.resultQueryClasses.MaxSalaryWorker;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MaxSalaryWorkerService {
    private PreparedStatement selectByMaxSalary;
    private Connection connection;
    private String sql = "SELECT name, salary FROM worker WHERE salary = ?";
    public MaxSalaryWorkerService(Database database)  {
        connection = database.getConnection();
    }
    public List<MaxSalaryWorker> findMaxSalaryWorkers() {
        List<MaxSalaryWorker> maxSalaryWorkerList = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            selectByMaxSalary = connection.prepareStatement(sql);
            selectByMaxSalary.setInt(1, findMaxSalary());
            resultSet = selectByMaxSalary.executeQuery();
            while (resultSet.next()) {
                maxSalaryWorkerList.add(new MaxSalaryWorker(
                        resultSet.getString("name"),
                        resultSet.getInt("salary")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { selectByMaxSalary.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (resultSet != null) { try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); } }
        }

        return maxSalaryWorkerList;
    }
    private int findMaxSalary() {
        String sql = "SELECT MAX(salary) AS max_salary FROM worker";

        try(ResultSet resultSet = Database.getInstance().executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt("max_salary");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}