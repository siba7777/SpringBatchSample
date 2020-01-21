package com.example.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import lombok.Data;

@Data
public class ConsoleReader implements ItemReader<List<String>> {

	Scanner scan;
	
	public ConsoleReader() {
		System.out.println("コンソールから入力データを入力して下さい。Enterキーで1レコード分入力完了です。");
		System.out.println("'q'を入力すると処理終了します。");
		scan = new Scanner(System.in);
	}
	
	@Override
	public List<String> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String inputLine = scan.nextLine();
        
        if (inputLine.equals("q")) {
        	scan.close();
        	return null;
        } else {
        	return new ArrayList<String>(Arrays.asList(inputLine));
        }
	}

}
