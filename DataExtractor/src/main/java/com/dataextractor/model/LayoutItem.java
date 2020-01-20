package com.dataextractor.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class LayoutItem implements Serializable {

	/**
	 *  レイアウトID
	 */
	private String layoutId;
	
	public LayoutItem() {
	}

}
