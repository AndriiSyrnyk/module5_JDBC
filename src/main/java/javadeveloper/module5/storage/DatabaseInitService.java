package javadeveloper.module5.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatabaseInitService {
    public static void main(String[] args) {
        String initDbFileName = "sql/init_db.sql.sql";
        String sqlExpression;

        try {
            sqlExpression = String.join("\n",
                    Files.readAllLines(Path.of(initDbFileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Database.getInstance().executeUpdate(sqlExpression);
    }
}