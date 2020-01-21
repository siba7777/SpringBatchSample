package com.example.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
 
@Component
public class ExampleJobExecutionListener implements JobExecutionListener {
 
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("BeforeJob");
    }
 
    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("AfterJob");
    }
 
}