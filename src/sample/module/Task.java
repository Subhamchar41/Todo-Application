package sample.module;

import java.sql.Timestamp;

public class Task {
   private  String task;
   private  String description;
   private Timestamp datecreated;
   private int id;
   private int taskId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Task(int id, String task, Timestamp datecreated, String description) {
        this.id=id;
        this.task = task;
        this.description = description;
        this.datecreated = datecreated;

    }

    public Task() {

    }


    public int getUserID() {
        return id;
    }


    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Timestamp datecreated) {
        this.datecreated = datecreated;
    }
}
