package com.dataextractor.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class Item implements Serializable {

	/**
	 *  ����ID
	 */
	private String id;
	
	/**
	 *  ���ږ�
	 */
	private String name;
	
	/**
	 *  ���ڒl
	 */
	private byte[] value;

	public Item() {
	}

}
