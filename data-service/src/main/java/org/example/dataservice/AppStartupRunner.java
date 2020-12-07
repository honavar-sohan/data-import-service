package org.example.dataservice;

import org.springframework.batch.core.*;
import org.example.dataservice.service.ReviewService;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AppStartupRunner implements ApplicationRunner {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // configure job parameters and check the job status
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        System.out.println("JobExecution: " + jobExecution.getStatus());

        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
    }
}