package com.dataextractor.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class LayoutItem implements Serializable {

	/**
	 *  ���C�A�E�gID
	 */
	private String layoutId;
	
	public LayoutItem() {
	}

}
