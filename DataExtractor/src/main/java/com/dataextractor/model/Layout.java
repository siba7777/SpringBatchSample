package com.dataextractor.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings({ "unused", "serial" })
public class Layout implements Serializable {

	/**
	 *  ���C�A�E�gID
	 */
	private String id;
	
	/**
	 *  ���C�A�E�g��
	 */
	private String name;
	
	/**
	 *  �t�@�C���G���R�[�h
	 */
	private String encode;

	public Layout() {
	}

}
