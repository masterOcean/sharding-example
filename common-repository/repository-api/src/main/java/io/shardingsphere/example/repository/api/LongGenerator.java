package io.shardingsphere.example.repository.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import io.shardingsphere.core.keygen.KeyGenerator;

public class LongGenerator implements KeyGenerator {
	
	private static int pe =0;
	private static Set<Number> number = new HashSet<Number>() ;
	
	@Override
	public Number generateKey() {
		Long ret = (long) 1;
		do {
			Date now = new Date();
			Random a =new Random(now.getTime());
			pe++;
			System.out.println(""+now.getTime()+","+pe);		
			ret =new Long( now.getTime()*1000+ Math.floorMod(a.nextInt(), 1000));		
		}while(number.contains(ret) );
		number.add(ret);
		return ret;
	}

}
