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

package io.shardingsphere.example.repository.jpa.repository;

import io.shardingsphere.example.repository.api.entity.OrderItem;
import io.shardingsphere.example.repository.api.repository.OrderItemRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
@Transactional
public class JPAOrderItemRepositoryImpl implements OrderItemRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void createTableIfNotExists() {
        throw new UnsupportedOperationException("createTableIfNotExists for JPA");
    }
    
    @Override
    public void truncateTable() {
        throw new UnsupportedOperationException("truncateTable for JPA");
    }
    
    @Override
    public void dropTable() {
        throw new UnsupportedOperationException("dropTable for JPA");
    }
    
    @Override
    public long insert(final OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem.getOrderItemId();
    }
    
    @Override
    public void delete(final Long orderItemId) {
        Query query = entityManager.createQuery("DELETE FROM OrderItemEntity i WHERE i.orderItemId = ?1 AND i.userId = 51");
        query.setParameter(1, orderItemId);
        query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<OrderItem> selectAll() {
        return (List<OrderItem>) entityManager.createQuery("SELECT i FROM  OrderItemEntity i ,OrderEntity o WHERE i.orderId = o.orderId").getResultList();
    
/*    	return (List<OrderItem>) entityManager.createQuery("SELECT i FROM OrderItemEntity i").getResultList();
*/    	}
    
    @SuppressWarnings("unchecked")
    @Override
    public List<OrderItem> selectRange() {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	return (List<OrderItem>) entityManager.createQuery("SELECT i FROM OrderEntity o, OrderItemEntity i WHERE o.orderId = i.orderId AND o.userId BETWEEN 0 AND 1000 ")
        			.getResultList();
        /*	return (List<OrderItem>) entityManager.createQuery("SELECT i FROM OrderEntity o, OrderItemEntity i WHERE o.orderId = i.orderId  ")
        			.getResultList();*/
			//return (List<OrderItem>) entityManager.createQuery("SELECT i FROM OrderEntity o, OrderItemEntity i WHERE o.orderId = i.orderId AND o.createTime BETWEEN ? AND ? ")
				//	.setParameter(1, df.parse("2019-10-28 00:00:00"))
				//	.setParameter(2, df.parse("2019-10-30 00:00:00")).getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
    }
}
