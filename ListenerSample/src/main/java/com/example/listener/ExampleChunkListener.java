package com.example.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ExampleChunkListener implements ChunkListener {

	@Override
	public void beforeChunk(ChunkContext context) {
		System.out.println("BeforeChunk");
		
	}

	@Override
	public void afterChunk(ChunkContext context) {
		System.out.println("AfterChunk");
		
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		System.out.println("AfterChunkError");
	}

}
