package com.solproe.taskmanager;

public interface TaskScheduler {
    void scheduleTask(String taskName, String folder, String schedule, String time, String... command) throws Exception;
    void deleteTask(String taskName) throws Exception;
}
