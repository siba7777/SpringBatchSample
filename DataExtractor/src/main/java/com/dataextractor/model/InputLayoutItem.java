package com.dataextractor.model;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class InputLayoutItem extends LayoutItem {

	/**
	 *  項目ID
	 */
	private String itemId;
	
	/**
	 *  位置
	 */
	private int postion;
	
	/**
	 *  最小桁長
	 */
	private int min_length;
	
	/**
	 *  最大桁長
	 */
	private int max_length;

	public InputLayoutItem() {
	}

}
