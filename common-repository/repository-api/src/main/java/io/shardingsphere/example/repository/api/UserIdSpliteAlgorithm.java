package io.shardingsphere.example.repository.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

public class UserIdSpliteAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
		// TODO Auto-generated method stub
		System.out.println("aaaaaaaaaaaaaa");
		Long b =shardingValue.getValue();
		//String a = "_"+b%2;
		/*System.out.println(shardingValue.getValue()+","+a);
		for(String each:availableTargetNames) {
			if(each.endsWith(a)) {
				System.out.println(each);
				return each;
			}
		}*/
		//DateFormat df = new SimpleDateFormat("yyyyMM");
		
		return "t_order_"+Math.floorMod(b.longValue(), 2L);
	
	}

}
