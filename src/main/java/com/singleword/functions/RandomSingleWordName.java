package com.singleword.functions;

import java.util.Random;

public class RandomSingleWordName implements RandomNameGeneratorInterface {

	private RandomSingleWordName() {}
	
	public static RandomSingleWordName getInstance() {
		return new RandomSingleWordName();
	}
	
	public String generateName() {
		Random random = new Random();
		int length = random.nextInt(7)+3;
		StringBuilder name = new StringBuilder();
		
		while(name.length()<length) {
			name.append((char)(random.nextInt(25)+65));
		}
		
		return name.toString();
	}

}
