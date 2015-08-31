package org.webflow.order;


import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.webflow.domain.OrderItemImpl;
import org.webflow.domain.Product;
import org.webflow.domain.User;

@Transactional(readOnly = true)
public interface OrderItemImplRepository extends JpaRepository<OrderItemImpl,Long>{
	
	List<OrderItemImpl> findAll();
	
	Page<OrderItemImpl> findAll(Pageable pageable);
	
	Page<OrderItemImpl> findByProduct(Product product, Pageable pageable);
	
	/**
	 * query generation from building query strategy
	 * @param user
	 * @param pageable
	 * @return
	 */
	@Query("select o from OrderItemImpl o where o.user.username = :username order by o.orderDate")
	Page<OrderItemImpl> findByUser(User user, Pageable pageable);
	
	/**
	 * query generation from method name strategy
	 * @param date
	 * @param pageable
	 * @return
	 */
	Page<OrderItemImpl> findByOrderDate(Date date, Pageable pageable);

}

