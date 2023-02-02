package javadeveloper.module5.databasePopulateServices;

import javadeveloper.module5.databaseTableClasses.Project;
import javadeveloper.module5.databaseTableClasses.Worker;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class WorkerService {
    private PreparedStatement insertSt;
    private Connection connection;

    public WorkerService(Database database) {
        connection = database.getConnection();
    }
    public void insertNewWorkers(List<Worker> workerList) {
        try {
            insertSt = connection.prepareStatement(
                    "INSERT INTO worker (id, name, birthday, level, salary) " +
                            "VALUES(?, ?, ?, ?, ?)"
            );
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
        } finally {
            try { insertSt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
