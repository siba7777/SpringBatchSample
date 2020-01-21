package com.dataextractor.converter;

import java.util.List;

import com.dataextractor.model.Item;

/**
 * 拡張値をそのまま出力する
 */
public class ExtensionValueConverter implements IConverter {

	public String convert(List<Item> itemList, String extension, String encode) {
		return extension;
	}
	
}
