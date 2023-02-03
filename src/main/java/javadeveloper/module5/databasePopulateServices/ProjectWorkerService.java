package javadeveloper.module5.databasePopulateServices;

import javadeveloper.module5.databaseTableClasses.ProjectWorker;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
public class ProjectWorkerService {
    private Database database;
    private String sqlQuery = "INSERT INTO project_worker (project_id, worker_id) VALUES(?, ?)";
    public ProjectWorkerService(Database db) {
        database = db;
    }
    public void insertNewProjectWorker(List<ProjectWorker> projectWorkerList) {
        try (Connection connection = database.getConnection();
             PreparedStatement insertSt = connection.prepareStatement(sqlQuery)){
            for (ProjectWorker projectWorker : projectWorkerList) {
                insertSt.setInt(1, projectWorker.getProjectId());
                insertSt.setInt(2, projectWorker.getWorkerId());
                insertSt.addBatch();
            }
            insertSt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}