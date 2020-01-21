package com.example.listener;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;

public class ToLoggerItemWriter implements ItemWriter<Object> {

	private static final Log log = LogFactory.getLog(ToLoggerItemWriter.class);
	
	public void write(List<? extends Object> data) throws Exception {
		log.info(data);
		for (Object item : data) {
			log.info("Input:"+ToStringBuilder.reflectionToString(item));
        }
	}

}
