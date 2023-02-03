package javadeveloper.module5.databasePopulateServices;
import javadeveloper.module5.databaseTableClasses.Project;
import javadeveloper.module5.storage.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
public class ProjectService {
    private Database database;
    private String sqlQuery = "INSERT INTO project (id, client_id, start_date, finish_date) " +
            "VALUES(?, ?, ?, ?)";
    public ProjectService(Database db) {
        database = db;
    }
    public void insertNewProjects(List<Project> projectList) {
        try(Connection connection = database.getConnection();
            PreparedStatement insertSt = connection.prepareStatement(sqlQuery)) {
            for (Project project : projectList) {
                insertSt.setInt(1, project.getId());
                insertSt.setInt(2, project.getClientId());
                insertSt.setString(3, project.getStartDate());
                insertSt.setString(4, project.getFinishDate());
                insertSt.addBatch();
            }
            insertSt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}