package javadeveloper.module5.storage;

import javadeveloper.module5.databasePopulateServices.*;
import javadeveloper.module5.databaseTableClasses.*;

import java.util.List;

public class DatabasePopulateService {
    public static void main(String[] args) {
        ClientService clientService = new ClientService(Database.getInstance());
        WorkerService workerService = new WorkerService(Database.getInstance());
        ProjectService projectService = new ProjectService(Database.getInstance());
        ProjectWorkerService projectWorkerService = new ProjectWorkerService(Database.getInstance());

        clientService.insertNewClients(initClientList());
        workerService.insertNewWorkers(initWorkerList());
        projectService.insertNewProjects(initProjectList());
        projectWorkerService.insertNewProjectWorker(initProjectWorkerList());
    }
    private static List<Client> initClientList() {
        return List.of(new Client(1, "james"),
                new Client(2, "mary"),
                new Client(3, "robert"),
                new Client(4, "patricia"),
                new Client(5, "john"));
    }

    private static List<Project> initProjectList() {
        return List.of(new Project(1, 1, "2023-01-05", "2023-05-05"),
                new Project (2, 1, "2023-01-10", "2023-05-10"),
                new Project(3, 1, "2023-01-12", "2024-07-12"),
                new Project(4, 2, "2023-01-15", "2024-06-15"),
                new Project(5, 2, "2023-01-16", "2025-03-16"),
                new Project(6, 3, "2023-01-18", "2025-02-18"),
                new Project(7, 3, "2023-01-20", "2026-08-20"),
                new Project(8, 4, "2023-01-25", "2026-10-25"),
                new Project(9, 4, "2023-01-30", "2027-10-30"),
                new Project(11, 4, "2023-01-30", "2028-10-30"),
                new Project(12, 5, "2023-01-30", "2028-10-30"),
                new Project(10, 5, "2023-01-31", "2027-07-01"));
    }

    private static List<Worker> initWorkerList() {
        return List.of(new Worker(1, "andrii", "1990-02-23", "Trainee", 999),
            new Worker(2, "oleh", "1995-07-12", "Junior", 999),
            new Worker(3, "anna", "1996-02-07", "Middle", 1500),
            new Worker(4, "serhii", "1999-08-11", "Senior", 2000),
            new Worker(5, "volodymyr", "1990-09-13", "Middle", 2000),
            new Worker(6, "marta", "1990-10-16", "Middle", 2050),
            new Worker(7, "stepan", "1990-11-29", "Middle", 2000),
            new Worker(8, "olha", "1990-12-08", "Junior", 1000),
            new Worker(9, "dima", "1901-04-15", "Senior", 5001),
            new Worker(10, "petro", "2000-04-15", "Senior", 5001));
    }

    private static List<ProjectWorker> initProjectWorkerList() {
        return List.of(new ProjectWorker(11, 9),
        new ProjectWorker(11, 10),
        new ProjectWorker(12, 9),
        new ProjectWorker(12, 10),
        new ProjectWorker(1, 1),
        new ProjectWorker(1, 2),
        new ProjectWorker(1, 3),
        new ProjectWorker(2, 3),
        new ProjectWorker(2, 9),
        new ProjectWorker(2, 4),
        new ProjectWorker(3, 5),
        new ProjectWorker(3, 1),
        new ProjectWorker(4, 5),
        new ProjectWorker(5, 6),
        new ProjectWorker(5, 7),
        new ProjectWorker(6, 7),
        new ProjectWorker(6, 8),
        new ProjectWorker(6, 9),
        new ProjectWorker(7, 4),
        new ProjectWorker(8, 1),
        new ProjectWorker(8, 2),
        new ProjectWorker(8, 3),
        new ProjectWorker(9, 9),
        new ProjectWorker(10, 6),
        new ProjectWorker(10, 7));
    }
}
