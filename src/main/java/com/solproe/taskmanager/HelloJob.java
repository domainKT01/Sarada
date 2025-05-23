package com.solproe.taskmanager;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("¡Hola desde HelloJob! - " + jobExecutionContext.getFireTime());
    }
}
