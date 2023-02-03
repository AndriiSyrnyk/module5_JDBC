package javadeveloper.module5.databasePopulateServices;

import javadeveloper.module5.databaseTableClasses.Worker;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
public class WorkerService {
    private Database database;
    private String sqlQuery = "INSERT INTO worker (id, name, birthday, level, salary) " +
            "VALUES(?, ?, ?, ?, ?)";
    public WorkerService(Database db) {
        database = db;
    }
    public void insertNewWorkers(List<Worker> workerList) {
        try (Connection connection = database.getConnection();
             PreparedStatement insertSt = connection.prepareStatement(sqlQuery)) {
            for (Worker worker : workerList) {
                insertSt.setInt(1, worker.getId());
                insertSt.setString(2, worker.getName());
                insertSt.setString(3, worker.getBirthday());
                insertSt.setString(4, worker.getLevel());
                insertSt.setInt(5, worker.getSalary());
                insertSt.addBatch();
            }
            insertSt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}