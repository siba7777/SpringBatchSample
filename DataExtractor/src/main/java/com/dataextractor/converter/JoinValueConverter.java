package com.dataextractor.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.dataextractor.model.Item;

/**
 * 項目値を結合して出力する
 */
public class JoinValueConverter implements IConverter {

	public String convert(List<Item> itemList, String extension, String encode) {
		
		String value = "";
		for (Item item : itemList) {
			try {
				value += new String(item.getValue(), encode);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return value;
		
	}
	
}
