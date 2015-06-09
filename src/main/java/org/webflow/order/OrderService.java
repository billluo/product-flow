package org.webflow.order;

import java.util.List;

import org.webflow.domain.OrderItem;
import org.webflow.domain.Product;
import org.webflow.domain.ProductImpl;
import org.webflow.domain.User;
import org.webflow.order.ProductCriteria;

//import java.util.List;

public interface OrderService {

    /**
     * Find orderItems made by the given user
     * @param name the user's name
     * @return their orderItems
     */
	List<OrderItem> findOrderItems(String name);
	//Page<OrderItem> findOrderItems(String name, Pageable pageable);

	User findUser(String username);
	
    /**
     * Find products available for booking by some criteria.
     * @param productCriteria the search criteria
     * @return a list of products meeting the criteria
     */
    public List<Product> findProducts(ProductCriteria productCriteria);

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
    public OrderItem createOrderItem(Long productId, String username);

    /**
     * Persist the orderItem to the database
     * @param orderItem the orderItem
     */
    public void persistOrderItem(OrderItem orderItem);

    /**
     * Cancel an existing orderItem.
     * @param id the orderItem id
     */
    public void cancelOrderItem(Long id);
    
	public void save(OrderItem orderItem);
	
	void calculateTotal(OrderItem orderItem);
	
	OrderItem createOrderItem(Long productId);



}
