package io.shardingsphere.example.repository.api;

import java.util.Collection;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

public class UserIdSpliteAlgorithm implements PreciseShardingAlgorithm<Long> {

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
		// TODO Auto-generated method stub
		System.out.println("aaaaaaaaaaaaaa");
		long b =shardingValue.getValue().longValue();
		String a = "_"+b%2;
		System.out.println(shardingValue.getValue()+","+a);
		for(String each:availableTargetNames) {
			if(each.endsWith(a)) {
				System.out.println(each);
				return each;
			}
		}
		return null;
	
	}

}
