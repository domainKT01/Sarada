package com.solproe.taskmanager;

public interface TaskScheduler {
    void scheduleTask(String taskName, String command, String schedule, String time) throws Exception;
    void deleteTask(String taskName) throws Exception;
}
