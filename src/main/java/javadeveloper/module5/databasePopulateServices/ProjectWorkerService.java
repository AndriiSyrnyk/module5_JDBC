package javadeveloper.module5.databasePopulateServices;

import javadeveloper.module5.databaseTableClasses.Client;
import javadeveloper.module5.databaseTableClasses.ProjectWorker;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProjectWorkerService {
    private PreparedStatement insertSt;
    private Connection connection;
    public ProjectWorkerService(Database database) throws SQLException {
        connection = database.getConnection();
        insertSt = connection.prepareStatement(
                "INSERT INTO project_worker (project_id, worker_id) VALUES(?, ?)"
        );
    }

    public void insertNewProjectWorker(List<ProjectWorker> projectWorkerList) {
        try {
            for (ProjectWorker projectWorker : projectWorkerList) {
                insertSt.setInt(1, projectWorker.getProjectId());
                insertSt.setInt(2, projectWorker.getWorkerId());
                insertSt.addBatch();
            }
            insertSt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { insertSt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

}
