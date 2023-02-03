package javadeveloper.module5.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitService {
    public static void main(String[] args) {
        String initDbFileName = "sql/init_db.sql.sql";
        String sqlInitDbExpression = "";

        try {
            sqlInitDbExpression = String.join("\n",
                    Files.readAllLines(Path.of(initDbFileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Database database = Database.getInstance();

        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlInitDbExpression);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}