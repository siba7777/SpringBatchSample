package com.dataextractor.reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.dataextractor.model.InputLayoutItem;
import com.dataextractor.model.Item;
import com.dataextractor.model.Layout;

import lombok.Data;

@Data
public class FixedFileItemReader implements ItemReader<List<Item>> {

	private String resource;
	private String mappingFile;
	private String mappingLayoutKey;
	private String mappingLayoutFile;
	private String mappingInputLayoutKey;
	private String mappingInputLayoutFile;
	private String mappingItemFileKey;
	private String mappingItemFileFile;
    
	private Layout layout;
	private List<InputLayoutItem> inputLayoutList;
	private List<Item> itemList;
	private BufferedInputStream inputStream;
    
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
        		mappingInputLayoutKey,
        		new File(mappingInputLayoutFile));

        InputLayoutItem item;
        inputLayoutList = new ArrayList<InputLayoutItem>();
        while ((item = (InputLayoutItem) reader.read()) != null) {
        	inputLayoutList.add(item);
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
	public List<Item> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		readLayout();
		if (this.inputStream == null) {
			this.inputStream = new BufferedInputStream(new FileInputStream(new File(resource)));
		}
		
		List<Item> inputItemList = new ArrayList<Item>();
		for (InputLayoutItem layoutItem : this.inputLayoutList) {
			byte[] bytes = new byte[layoutItem.getMax_length()];
			
			if (this.inputStream.read(bytes) != -1) {
				Optional<Item> item = this.itemList.stream().filter(i -> i.getId().equals(layoutItem.getItemId())).findFirst();
				item.ifPresent(i -> {
					Item inputItem = SerializationUtils.clone(i);
					inputItem.setValue(ArrayUtils.clone(bytes));
					inputItemList.add(inputItem);
			    });
			} else {
				this.inputStream.close();
				return null;
			}
		}
		
		return inputItemList;
	}

}
