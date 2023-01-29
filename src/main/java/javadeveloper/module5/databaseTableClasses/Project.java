package javadeveloper.module5.databaseTableClasses;

public class Project {
    private int id;
    private int clientId;
    private String startDate;
    private String finishDate;

    public Project(int id, int clientId, String startDate, String finishDate) {
        this.id = id;
        this.clientId = clientId;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }
}
