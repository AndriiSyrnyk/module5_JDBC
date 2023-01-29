package javadeveloper.module5.databaseTableClasses;

public class Worker {
    private int id;
    private String name;
    private String birthday;
    private String level;
    private int salary;

    public int getId() {
        return id;
    }

    public Worker(int id, String name, String birthday, String level, int salary) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.level = level;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLevel() {
        return level;
    }

    public int getSalary() {
        return salary;
    }
}
