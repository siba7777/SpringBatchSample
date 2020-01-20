package com.dataextractor.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class Item implements Serializable {

	/**
	 *  €–ÚID
	 */
	private String id;
	
	/**
	 *  €–Ú–¼
	 */
	private String name;
	
	/**
	 *  €–Ú’l
	 */
	private byte[] value;

	public Item() {
	}

}
