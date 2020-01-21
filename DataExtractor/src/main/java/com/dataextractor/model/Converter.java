package com.dataextractor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.ArrayUtils;

import com.dataextractor.converter.IConverter;
import com.dataextractor.converter.JoinValueConverter;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class Converter implements Serializable {

	/**
	 *  クラス
	 */
	private String classPath;
	
	/**
	 *  拡張値
	 */
	private String extension;
	
	/**
	 *  項目IDリスト
	 */
	private String itemIds;
	
	/**
	 *  項目リスト
	 */
	private List<Item> itemList;

	public Converter() {
		this.itemList = new ArrayList<Item>();
	}
	
	/**
	 * 項目定義を追加
	 * 
	 * @param item 項目定義
	 */
	public void appendItem(Item item) {
		this.itemList.add(item);
	}

	/**
	 * 入力項目値を設定
	 * 
	 * @param inItemList 入力項目値が設定されている項目リスト
	 */
	public void setItemValues(List<Item> inItemList) {
		for (Item inItem : inItemList) {
			for (Item item : this.itemList) {
				if (item.getId().equals(inItem.getId())) {
					item.setValue(ArrayUtils.clone(inItem.getValue()));
					break;
				}
			}
		}
	}
	
	/**
	 * 設定されている項目リストの値と拡張値を元に、
	 * コンバータ定義で定義されているクラスの変換処理を実行して、値を編集する。
	 * 
	 * @param encode 文字列エンコード
	 * @return 変換後文字列
	 */
	public String convert(String encode) {
		try {
			Class<?> converterClazz = Class.forName(this.classPath);
			IConverter converter = (IConverter) converterClazz.newInstance();
			return converter.convert(this.itemList, this.extension, encode);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return "";
	}
}
