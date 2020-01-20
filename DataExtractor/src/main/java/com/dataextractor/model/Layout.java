package com.dataextractor.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class Layout implements Serializable {

	/**
	 *  レイアウトID
	 */
	private String id;
	
	/**
	 *  レイアウト名
	 */
	private String name;
	
	/**
	 *  ファイルエンコード
	 */
	private String encode;

	public Layout() {
	}

}
