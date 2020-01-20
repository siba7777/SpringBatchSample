package com.dataextractor.model;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class OutputLayoutItem extends LayoutItem {

	/**
	 *  コンバータ
	 */
	private Converter converter;

	public OutputLayoutItem() {
	}

}
