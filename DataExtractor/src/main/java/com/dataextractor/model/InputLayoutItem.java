package com.dataextractor.model;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class InputLayoutItem extends LayoutItem {

	/**
	 *  ����ID
	 */
	private String itemId;
	
	/**
	 *  �ʒu
	 */
	private int postion;
	
	/**
	 *  �ŏ�����
	 */
	private int min_length;
	
	/**
	 *  �ő包��
	 */
	private int max_length;

	public InputLayoutItem() {
	}

}
