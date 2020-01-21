package com.dataextractor.converter;

import java.util.List;

import com.dataextractor.model.Item;

/**
 * 拡張値で指定された改行コードを出力する
 */
public class LineFeedConverter implements IConverter {

	public String convert(List<Item> itemList, String extension, String encode) {
		if (extension.equals("\\r\\n"))
		{
			return "\r\n";
		} else if (extension.equals("\\n"))
		{
			return "\n";
		} else if (extension.equals("\\r"))
		{
			return "\r";
		} else {
			return extension;
		}
	}
	
}
