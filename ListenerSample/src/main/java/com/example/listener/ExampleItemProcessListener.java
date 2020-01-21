package com.example.listener;

import java.util.List;

import org.springframework.batch.core.ItemProcessListener;
 
public class ExampleItemProcessListener implements ItemProcessListener<List<String>, List<? extends Object>> {
 
    @Override
    public void beforeProcess(List<String> items) {
        System.out.println("BeforeProcess");
    }
 
    @Override
    public void afterProcess(List<String> items, List<? extends Object> result) {
        System.out.println("AfterProcess");
    }
 
    @Override
    public void onProcessError(List<String> items, Exception e) {
        System.out.println("OnProcessError");
    }
}
