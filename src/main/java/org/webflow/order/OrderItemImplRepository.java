package org.webflow.order;


import java.sql.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import org.webflow.domain.OrderItemImpl;
import org.webflow.domain.Product;
import org.webflow.domain.User;

@Transactional(readOnly = true)
public interface OrderItemImplRepository extends CrudRepository<OrderItemImpl,Long>{
	
	Page<OrderItemImpl> findAll(Pageable pageable);
	
	Page<OrderItemImpl> findByProduct(Product product, Pageable pageable);
	
	@Query("select o from OrderItemImpl o where o.user.username = :username order by o.orderDate")
	Page<OrderItemImpl> findByUser(User user, Pageable pageable);
	
	Page<OrderItemImpl> findByOrderDate(Date date, Pageable pageable);

}

