package javadeveloper.module5.databaseTableClasses;

public class ProjectWorker {
    private int projectId;
    private int workerId;
    public ProjectWorker(int projectId, int workerId) {
        this.projectId = projectId;
        this.workerId = workerId;
    }
    public int getProjectId() {
        return projectId;
    }
    public int getWorkerId() {
        return workerId;
    }
}