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
	 *  �N���X
	 */
	private String classPath;
	
	/**
	 *  �g���l
	 */
	private String extension;
	
	/**
	 *  ����ID���X�g
	 */
	private String itemIds;
	
	/**
	 *  ���ڃ��X�g
	 */
	private List<Item> itemList;

	public Converter() {
		this.itemList = new ArrayList<Item>();
	}
	
	public void appendItem(Item item) {
		this.itemList.add(item);
	}

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
