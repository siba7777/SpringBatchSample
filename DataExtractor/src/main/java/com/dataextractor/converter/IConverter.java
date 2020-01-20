package com.dataextractor.converter;

import java.util.List;

import com.dataextractor.model.Item;

public interface IConverter {

	String convert(List<Item> itemList, String extension, String encode);
	
}
