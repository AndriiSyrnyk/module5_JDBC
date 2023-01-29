package javadeveloper.module5;
import javadeveloper.module5.databaseResultQueryServices.*;
import javadeveloper.module5.storage.Database;
import java.sql.SQLException;
public class App {
    public static void main(String[] args) {
        try {
            System.out.println(new MaxSalaryWorkerService(Database.getInstance()).findMaxSalaryWorkers());
            System.out.println(new MaxProjectCountClientService(Database.getInstance()).findMaxProjectCountClients());
            System.out.println(new LongestProjectService(Database.getInstance()).findLongestProjects());
            System.out.println(new YoungestEldestWorkerService(Database.getInstance()).findYoungestEldestWorker());
            System.out.println(new ProjectPricesService(Database.getInstance()).printProjectPrices());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
