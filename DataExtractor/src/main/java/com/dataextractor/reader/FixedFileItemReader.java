package com.dataextractor.reader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.dataextractor.model.InputLayoutItem;
import com.dataextractor.model.Item;
import com.dataextractor.model.Layout;
import com.dataextractor.util.LayoutUtils;

import lombok.Data;

@Data
public class FixedFileItemReader implements ItemReader<List<Item>> {

	private String resource;
	private String mappingFile;
	private String mappingLayoutId;
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
    
	/**
	 *  マスタとなるレイアウト読込
	 */
	private void readLayout() {
		
		// 一度だけ取得
		if (this.layout == null) {		
			this.layout = LayoutUtils.readLayout(this.mappingFile, this.mappingLayoutId, this.mappingLayoutKey, this.mappingLayoutFile);
			this.inputLayoutList = LayoutUtils.readInputLayoutList(this.mappingFile, this.mappingLayoutId, this.mappingInputLayoutKey, this.mappingInputLayoutFile);
			this.itemList = LayoutUtils.readInputItemList(this.mappingFile, this.mappingItemFileKey, this.mappingItemFileFile, this.inputLayoutList);
		}
		
    }
	
	/**
	 *  固定長ファイル読み込み
	 *  バイナリデータとして、レイアウトで定義されている項目値長に沿ってファイルを読み込む
	 */
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
