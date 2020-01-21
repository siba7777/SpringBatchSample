package com.dataextractor.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class Item implements Serializable {

	/**
	 *  項目ID
	 */
	private String id;
	
	/**
	 *  項目名
	 */
	private String name;
	
	/**
	 *  項目値
	 */
	private byte[] value;

	public Item() {
	}

}
