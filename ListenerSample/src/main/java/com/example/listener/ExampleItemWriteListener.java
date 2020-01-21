package com.example.listener;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.batch.core.ItemWriteListener;

public class ExampleItemWriteListener implements ItemWriteListener<String> {

    @Override
    public void beforeWrite(List<? extends String> items) {
        System.out.println("BeforeWrite");
    }

    @Override
    public void afterWrite(List<? extends String> items) {
        System.out.println("AfterWrite");
		for (Object item : items) {
			System.out.println("Input:"+ToStringBuilder.reflectionToString(item));
        }
    }

    @Override
    public void onWriteError(Exception exception, List<? extends String> items) {
        System.out.println("OnWriteError");
    }

}
