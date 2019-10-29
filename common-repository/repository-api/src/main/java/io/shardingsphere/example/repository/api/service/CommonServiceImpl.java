/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.example.repository.api.service;

import io.shardingsphere.example.repository.api.LongGenerator;
import io.shardingsphere.example.repository.api.entity.Order;
import io.shardingsphere.example.repository.api.entity.OrderItem;
import io.shardingsphere.example.repository.api.repository.OrderItemRepository;
import io.shardingsphere.example.repository.api.repository.OrderRepository;
import io.shardingsphere.transaction.annotation.ShardingTransactionType;
import io.shardingsphere.transaction.api.TransactionType;

import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public abstract class CommonServiceImpl implements CommonService {
    
    @Override
    public void initEnvironment() {
        getOrderRepository().createTableIfNotExists();
        getOrderItemRepository().createTableIfNotExists();
        getOrderRepository().truncateTable();
        getOrderItemRepository().truncateTable();
    }
    
    @Override
    public void cleanEnvironment() {
        getOrderRepository().dropTable();
        getOrderItemRepository().dropTable();
    }
    
    @Transactional
    @Override
    @ShardingTransactionType(TransactionType.XA)
    public void processSuccess(final boolean isRangeSharding) {
        System.out.println("-------------- Process Success Begin ---------------");
        List<Long> orderIds = insertData();
        printData(isRangeSharding);
        //deleteData(orderIds);
        //printData(isRangeSharding);
        System.out.println("-------------- Process Success Finish --------------");
    }
    
    @Transactional
    @Override
    public void processFailure() {
        System.out.println("-------------- Process Failure Begin ---------------");
        insertData();
        System.out.println("-------------- Process Failure Finish --------------");
        throw new RuntimeException("Exception occur for transaction test.");
    }
    
	private List<Long> insertData() {
		System.out.println("---------------------------- Insert Data ----------------------------");
		List<Long> result = new ArrayList<>(10);
		try {
			Date d1 = new Date();
			String s2 = "201911";
			String s3 = "201912";
			DateFormat df = new SimpleDateFormat("yyyyMM");

			Date d2 = df.parse(s2);
			Date d3 = df.parse(s3);
			List<Date> dl = new ArrayList<Date>();
			dl.add(d1);
			dl.add(d2);
			dl.add(d3);
			Random r = new Random();
			for (int i = 1; i <= 10; i++) {
				Order order = newOrder();
				LongGenerator gen = new LongGenerator();
				order.setOrderId((Long) gen.generateKey());
				order.setUserId(i);
				order.setStatus("INSERT_TEST");
				int j = r.nextInt();
				//order.setCreateTime(dl.get(Math.floorMod(j, 3)));

				getOrderRepository().insert(order);
				OrderItem item = newOrderItem();
				item.setOrderItemId((Long) gen.generateKey());
				item.setOrderId(order.getOrderId());
				item.setUserId(i);
				item.setStatus("INSERT_TEST");
				getOrderItemRepository().insert(item);

				// result.add(order.getOrderId());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
    
    private void deleteData(final List<Long> orderIds) {
        System.out.println("---------------------------- Delete Data ----------------------------");
        for (Long each : orderIds) {
            getOrderRepository().delete(each);
            getOrderItemRepository().delete(each);
        }
    }
    
    @Override
    public void printData(final boolean isRangeSharding) {
        if (isRangeSharding) {
            printDataRange();
        } else {
            printDataAll();
        }
    }
    
    private void printDataRange() {
        System.out.println("---------------------------- Print Order Data -----------------------");
        for (Object each : getOrderRepository().selectRange()) {
            System.out.println("orderId+"+ each);
        }
        System.out.println("---------------------------- Print OrderItem Data -------------------");
        for (Object each : getOrderItemRepository().selectRange()) {
            System.out.println("orderItemId+"+each);
        }
    }
    
    private void printDataAll() {
        System.out.println("---------------------------- Print Order Data -----------------------");
        for (Object each : getOrderRepository().selectAll()) {
            System.out.println(each);
        }
        System.out.println("---------------------------- Print OrderItem Data -------------------");
        for (Object each : getOrderItemRepository().selectAll()) {
            System.out.println(each);
        }
    }
    
    protected abstract OrderRepository getOrderRepository();
    
    protected abstract OrderItemRepository getOrderItemRepository();
    
    protected abstract Order newOrder();
    
    protected abstract OrderItem newOrderItem();
}
