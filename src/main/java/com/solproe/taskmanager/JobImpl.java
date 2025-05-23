package com.solproe.taskmanager;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class JobImpl {

    public void implJob() throws SchedulerException {
        // 1. Crear un SchedulerFactory
        SchedulerFactory sf = new StdSchedulerFactory();

        // 2. Obtener un Scheduler
        Scheduler scheduler = sf.getScheduler();

        // 3. Definir el JobDetail
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("helloJob", "group1") // Nombre y grupo del job (identificador único)
                .build();

        // 4. Definir el Trigger (ejecutar una vez después de 3 segundos)
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleTrigger", "group1")
                .startAt(DateBuilder.futureDate(3, DateBuilder.IntervalUnit.SECOND))
                .build();

        // 5. Programar el job con el trigger
        scheduler.scheduleJob(job, trigger);

        // 6. Iniciar el scheduler
        scheduler.start();
    }
}
