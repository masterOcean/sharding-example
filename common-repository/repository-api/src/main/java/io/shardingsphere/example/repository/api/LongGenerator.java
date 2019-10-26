package io.shardingsphere.example.repository.api;

import java.util.Date;
import java.util.Random;

import io.shardingsphere.core.keygen.KeyGenerator;

public class LongGenerator implements KeyGenerator {
	
	
	@Override
	public Number generateKey() {
		Date now = new Date();
		Random a =new Random(now.getTime());
		System.out.println(""+now.getTime());
		return now.getTime()*1000+ a.nextInt()%999;
	}

}
