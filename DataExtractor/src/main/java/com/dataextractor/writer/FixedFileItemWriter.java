package com.dataextractor.writer;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.dataextractor.model.Item;
import com.dataextractor.model.Layout;
import com.dataextractor.model.OutputLayoutItem;
import com.dataextractor.util.LayoutUtils;

import lombok.Data;

@Data
public class FixedFileItemWriter implements ItemWriter<Object> {

	private String outFile;
	private String mappingFile;
	private String mappingLayoutId;
	private String mappingLayoutKey;
	private String mappingLayoutFile;
	private String mappingOutputLayoutKey;
	private String mappingOutputLayoutFile;
	private String mappingItemFileKey;
	private String mappingItemFileFile;
    
	private Layout layout;
	private List<OutputLayoutItem> outputLayoutList;
	private List<Item> itemList;
    
	/**
	 *  マスタとなるレイアウト読込
	 */
	private void readLayout() {
		
		// 一度だけ取得
		if (this.layout == null) {
			this.layout = LayoutUtils.readLayout(this.mappingFile, this.mappingLayoutId, this.mappingLayoutKey, this.mappingLayoutFile);
			this.itemList = LayoutUtils.readItemList(this.mappingFile, this.mappingItemFileKey, this.mappingItemFileFile);
			this.outputLayoutList = LayoutUtils.readOutputLayoutList(this.mappingFile, this.mappingLayoutId, this.mappingOutputLayoutKey, this.mappingOutputLayoutFile, this.itemList);
		}
		
    }
	
	/**
	 *  固定長ファイル出力
	 *  出力レイアウトで定義されている項目毎に、コンバータを実行してファイル出力する
	 */
	@Override
	public void write(List<? extends Object> recordList) throws Exception {
		readLayout();

		try(BufferedWriter outputWriter =
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile, true), this.layout.getEncode()));) {

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
