package com.dataextractor.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.springframework.batch.item.ItemWriter;

import com.dataextractor.model.Item;
import com.dataextractor.model.Layout;
import com.dataextractor.model.OutputLayoutItem;

import lombok.Data;

@Data
public class FixedFileItemWriter implements ItemWriter<Object> {

	private String outFile;
	private String mappingFile;
	private String mappingLayoutKey;
	private String mappingLayoutFile;
	private String mappingOutputLayoutKey;
	private String mappingOutputLayoutFile;
	private String mappingItemFileKey;
	private String mappingItemFileFile;
    
	private Layout layout;
	private List<OutputLayoutItem> outputLayoutList;
	private List<Item> itemList;
    
	public void readLayout() {
		
		if (this.layout == null) {
			StreamFactory factory = StreamFactory.newInstance();
			factory.load(this.mappingFile);
			
			readLayout(factory);
			readInputLayoutList(factory);
			readItemList(factory);
		}
		
    }
	
	protected void readLayout(StreamFactory factory) {
        BeanReader reader = factory.createReader(
        		mappingLayoutKey,
        		new File(mappingLayoutFile));

        this.layout = (Layout) reader.read();
        reader.close();
	}
	
	protected void readInputLayoutList(StreamFactory factory) {
        BeanReader reader = factory.createReader(
        		mappingOutputLayoutKey,
        		new File(mappingOutputLayoutFile));

        OutputLayoutItem item;
        outputLayoutList = new ArrayList<OutputLayoutItem>();
        while ((item = (OutputLayoutItem) reader.read()) != null) {
        	outputLayoutList.add(item);
        }
        reader.close();
	}
	
	protected void readItemList(StreamFactory factory) {
        BeanReader reader = factory.createReader(
        		mappingItemFileKey,
        		new File(mappingItemFileFile));

        Item item;
        itemList = new ArrayList<Item>();
        while ((item = (Item) reader.read()) != null) {
        	itemList.add(item);
        }
        reader.close();
	}
	
	@Override
	public void write(List<? extends Object> recordList) throws Exception {
		readLayout();

		try(BufferedWriter outputWriter =
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile, true), this.layout.getEncode()));) {
			
			for (OutputLayoutItem layoutItem : this.outputLayoutList) {
				List<Item> convertItemList = new ArrayList<Item>();
				for (String itemId : layoutItem.getConverter().getItemIds().split(",")) {
					Optional<Item> item = this.itemList.stream().filter(i -> i.getId().equals(itemId)).findFirst();
					item.ifPresent(i -> {
						Item outputItem = SerializationUtils.clone(i);
						convertItemList.add(outputItem);
					});
				}
				layoutItem.getConverter().setItemList(convertItemList);
			}

			for (Object record : recordList) {

				@SuppressWarnings("unchecked")
				List<Item> inputItemList = (List<Item>) record;

				for (OutputLayoutItem layoutItem : this.outputLayoutList) {
					layoutItem.getConverter().setItemValues(inputItemList);
					outputWriter.write(layoutItem.getConverter().convert(this.layout.getEncode()));
				}
			}
			
		}
	}

}
