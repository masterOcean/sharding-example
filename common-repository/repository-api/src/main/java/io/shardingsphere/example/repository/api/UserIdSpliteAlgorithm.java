package io.shardingsphere.example.repository.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

public class UserIdSpliteAlgorithm implements PreciseShardingAlgorithm<Date> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
		// TODO Auto-generated method stub
		System.out.println("aaaaaaaaaaaaaa");
		Date b =shardingValue.getValue();
		//String a = "_"+b%2;
		/*System.out.println(shardingValue.getValue()+","+a);
		for(String each:availableTargetNames) {
			if(each.endsWith(a)) {
				System.out.println(each);
				return each;
			}
		}*/
		DateFormat df = new SimpleDateFormat("yyyyMM");
		return "t_order_"+df.format(b);
	
	}

}
