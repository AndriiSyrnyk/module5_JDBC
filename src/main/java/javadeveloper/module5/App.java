package javadeveloper.module5;
import javadeveloper.module5.databaseResultQueryServices.*;
import javadeveloper.module5.storage.Database;
import java.sql.SQLException;
public class App {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        try {
            System.out.println(new MaxSalaryWorkerService(database).findMaxSalaryWorkers());
            System.out.println(new MaxProjectCountClientService(database).findMaxProjectCountClients());
            System.out.println(new LongestProjectService(database).findLongestProjects());
            System.out.println(new YoungestEldestWorkerService(database).findYoungestEldestWorker());
            System.out.println(new ProjectPricesService(database).printProjectPrices());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
    }
}
