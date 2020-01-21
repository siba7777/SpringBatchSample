package com.dataextractor.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.SerializationUtils;
import org.beanio.BeanReader;
import org.beanio.StreamFactory;

import com.dataextractor.model.InputLayoutItem;
import com.dataextractor.model.Item;
import com.dataextractor.model.Layout;
import com.dataextractor.model.OutputLayoutItem;

public class LayoutUtils {

	/**
	 * ファイルデータを読んでマッピング定義に沿ってマッピングするリーダを作成
	 * 
	 * @param mappingFile マッピング定義ファイルパス
	 * @param mappingLayoutKey マッピング定義レイアウトキー
	 * @param mappingLayoutFile レイアウト定義ファイルパス
	 * @return リーダ
	 */
	private static BeanReader readerFactory(String mappingFile, String mappingLayoutKey, String mappingLayoutFile) {
		StreamFactory factory = StreamFactory.newInstance();
		factory.load(mappingFile);
		
		BeanReader reader = factory.createReader(
        		mappingLayoutKey,
        		new File(mappingLayoutFile));
		return reader;
	}
	
	/**
	 * レイアウト定義を読み込み
	 * 
	 * @param mappingFile マッピング定義ファイルパス
	 * @param layoutId レイアウトID
	 * @param mappingLayoutKey マッピング定義レイアウトキー
	 * @param mappingLayoutFile レイアウト定義ファイルパス
	 * @return レイアウト定義
	 */
	public static Layout readLayout(String mappingFile, String layoutId, String mappingLayoutKey, String mappingLayoutFile) {
		BeanReader reader = readerFactory(mappingFile, mappingLayoutKey, mappingLayoutFile);

		Layout layout = null;
        while ((layout = (Layout) reader.read()) != null) {
        	if (layout.getId().equals(layoutId)) {
        		return layout;
        	}
        }
        return layout;
	}
	
	/**
	 * 入力データレイアウト項目定義を読み込み
	 * @param mappingFile マッピング定義ファイルパス
	 * @param layoutId レイアウトID
	 * @param mappingLayoutKey マッピング定義レイアウトキー
	 * @param mappingLayoutFile 入力データレイアウト項目定義ファイルパス
	 * @return 入力データレイアウト項目定義リスト
	 */
	public static List<InputLayoutItem> readInputLayoutList(String mappingFile, String layoutId, String mappingLayoutKey, String mappingLayoutFile) {
		BeanReader reader = readerFactory(mappingFile, mappingLayoutKey, mappingLayoutFile);
		
        InputLayoutItem item;
        List<InputLayoutItem> inputLayoutList = new ArrayList<InputLayoutItem>();
        while ((item = (InputLayoutItem) reader.read()) != null) {
        	if (item.getLayoutId().equals(layoutId)) {
        		inputLayoutList.add(item);
        	}
        }
        reader.close();
        
        return inputLayoutList;
	}
	
	/**
	 * 出力データレイアウト項目定義を読み込み
	 * @param mappingFile マッピング定義ファイルパス
	 * @param layoutId レイアウトID
	 * @param mappingLayoutKey マッピング定義レイアウトキー
	 * @param mappingLayoutFile 出力データレイアウト項目定義ファイルパス
	 * @param itemList 項目定義リスト
	 * @return 出力データレイアウト項目定義リスト
	 */
	public static List<OutputLayoutItem> readOutputLayoutList(String mappingFile, String layoutId, String mappingLayoutKey, String mappingLayoutFile, List<Item> itemList) {
		BeanReader reader = readerFactory(mappingFile, mappingLayoutKey, mappingLayoutFile);
		
		OutputLayoutItem layoutItem;
        List<OutputLayoutItem> outputLayoutList = new ArrayList<OutputLayoutItem>();
        while ((layoutItem = (OutputLayoutItem) reader.read()) != null) {
        	if (layoutItem.getLayoutId().equals(layoutId)) {
        		
				List<Item> convertItemList = new ArrayList<Item>();
				for (String itemId : layoutItem.getConverter().getItemIds().split(",")) {
					Optional<Item> item = itemList.stream().filter(i -> i.getId().equals(itemId)).findFirst();
					item.ifPresent(i -> {
						convertItemList.add(SerializationUtils.clone(i));
					});
				}
				layoutItem.getConverter().setItemList(convertItemList);
				
        		outputLayoutList.add(layoutItem);
        	}
        }
        reader.close();
        
        return outputLayoutList;
	}
	
	/**
	 * 入力データ項目定義を読み込み
	 * 
	 * @param mappingFile マッピング定義ファイルパス
	 * @param mappingLayoutKey マッピング定義レイアウトキー
	 * @param mappingLayoutFile 項目定義ファイルパス
	 * @param layoutItemList
	 * @return 入力データレイアウト項目定義リスト
	 */
	public static List<Item> readInputItemList(String mappingFile, String mappingLayoutKey, String mappingLayoutFile, List<InputLayoutItem> layoutItemList) {
		BeanReader reader = readerFactory(mappingFile, mappingLayoutKey, mappingLayoutFile);

        Item item;
        List<Item> itemList = new ArrayList<Item>();
        while ((item = (Item) reader.read()) != null) {
        	for (InputLayoutItem layoutItem : layoutItemList) {
        		if (layoutItem.getItemId().equals(item.getId())) {
        			itemList.add(item);
        		}
        	}
        }
        reader.close();
        
        return itemList;
	}
	
	/**
	 * 項目定義を読み込み
	 * @param mappingFile マッピング定義ファイルパス
	 * @param mappingLayoutKey マッピング定義レイアウトキー
	 * @param mappingLayoutFile 項目定義ファイルパス
	 * @return 項目定義リスト
	 */
	public static List<Item> readItemList(String mappingFile, String mappingLayoutKey, String mappingLayoutFile) {
		BeanReader reader = readerFactory(mappingFile, mappingLayoutKey, mappingLayoutFile);

		Item item;
		List<Item> itemList = new ArrayList<Item>();
		while ((item = (Item) reader.read()) != null) {
			itemList.add(item);
		}
		reader.close();

		return itemList;
	}
}
