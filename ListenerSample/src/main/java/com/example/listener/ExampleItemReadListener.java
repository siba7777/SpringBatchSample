package com.example.listener;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.batch.core.ItemReadListener;

public class ExampleItemReadListener implements ItemReadListener<List<String>> {

    @Override
    public void beforeRead() {
        System.out.println("BeforeRead");
    }

    @Override
    public void afterRead(List<String> items) {
        System.out.println("AfterRead");
		for (Object item : items) {
			System.out.println("Input:"+ToStringBuilder.reflectionToString(item));
        }
    }

    @Override
    public void onReadError(Exception ex) {
        System.out.println("OnReadError");
    }

}
