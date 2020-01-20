package com.dataextractor.model;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class ConverterTest {

	@Test
	public void test() {
		final String encode = "utf-8";

		Converter converter = new Converter();
		converter.setClassPath("com.dataextractor.converter.JoinValueConverter");
		converter.setExtension("");
		
		try {
			Item item1 = new Item();
			item1.setId("1");
			item1.setName("item" + item1.getId());

			item1.setValue("hello".getBytes(encode));

			converter.appendItem(item1);

			Item item2 = new Item();
			item2.setId("2");
			item2.setName("item" + item2.getId());
			item2.setValue("hoge".getBytes(encode));
			converter.appendItem(item2);
		} catch (UnsupportedEncodingException e) {
		}

		assertEquals("hellohoge", converter.convert(encode));
	}

}
