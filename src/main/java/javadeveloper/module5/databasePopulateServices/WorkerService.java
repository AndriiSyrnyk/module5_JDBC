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

    public WorkerService(Database database) throws SQLException {
        Connection connection = database.getConnection();

        insertSt = connection.prepareStatement(
                "INSERT INTO worker (id, name, birthday, level, salary) " +
                        "VALUES(?, ?, ?, ?, ?)"
        );
    }

    public void insertNewWorkers(List<Worker> workerList) throws SQLException {
        for (Worker worker : workerList) {
            insertSt.setInt(1, worker.getId());
            insertSt.setString(2, worker.getName());
            insertSt.setString(3, worker.getBirthday());
            insertSt.setString(4, worker.getLevel());
            insertSt.setInt(5, worker.getSalary());
            insertSt.addBatch();
        }

        insertSt.executeBatch();
    }
}
