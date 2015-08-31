package org.webflow.order;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.webflow.domain.OrderItem;
import org.webflow.domain.OrderItemImpl;
import org.webflow.domain.Product;
import org.webflow.domain.ProductImpl;
import org.webflow.domain.User;
import org.webflow.order.ProductCriteria;

//import java.util.List;

/**
 * @author bluo
 *
 */
public interface OrderService {

    /**
     * Find orderItems made by the given user
     * @param name the user's name
     * @return their orderItems
     */
	List<OrderItemImpl> findOrderItems(String name);
	
	Page<OrderItemImpl> findOrderItems(String name, Pageable pageable);

	User findUser(String username);
	
    /**
     * Find products available for booking by some criteria.
     * @param productCriteria the search criteria
     * @return a list of products meeting the criteria
     */
    public List<ProductImpl> findProducts(ProductCriteria productCriteria);

    /**
     * Find product by their identifier.
     * @param id the product id
     * @return the product
     */
    public ProductImpl findProductById(Long id);

    /**
     * Create a new, transient product orderItem instance for the given user.
     * @param productId the productId
     * @param userName the user name
     * @return the new transient orderItem instance
     */
    public OrderItemImpl createOrderItem(Long productId, String username);

    /**
     * Persist the orderItem to the database
     * @param orderItem the orderItem
     * use save() method
     */
//    public void persistOrderItem(OrderItem orderItem);

    /**
     * Cancel an existing orderItem.
     * @param id the orderItem id
     */
    public void cancelOrderItem(Long id);
    
	public void save(OrderItemImpl orderItem);
	
	/**
	 * @param orderItem
	 * calculate the total price of order items
	 */
	void calculateTotal(OrderItemImpl orderItem);
	
	/**
	 * @param productId
	 * @return a new order item
	 */
	OrderItem createOrderItem(Long productId);



}
