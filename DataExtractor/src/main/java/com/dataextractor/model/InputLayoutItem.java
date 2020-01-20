package com.dataextractor.model;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class InputLayoutItem extends LayoutItem {

	/**
	 *  €–ÚID
	 */
	private String itemId;
	
	/**
	 *  ˆÊ’u
	 */
	private int postion;
	
	/**
	 *  Å¬Œ…’·
	 */
	private int min_length;
	
	/**
	 *  Å‘åŒ…’·
	 */
	private int max_length;

	public InputLayoutItem() {
	}

}
