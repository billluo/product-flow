package org.webflow.order;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Component;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.webflow.admin.UserRepository;
import org.webflow.domain.OrderItem;
import org.webflow.domain.OrderItemImpl;
import org.webflow.domain.Product;
import org.webflow.domain.ProductImpl;
import org.webflow.domain.User;
import org.webflow.order.ProductCriteria;
import org.webflow.order.OrderItemImplRepository;
import org.webflow.order.ProductImplRepository;

/**
 * A JPA-based implementation of the Ordering Service. Delegates to a JPA entity manager to issue data access calls
 * against the backing repository. The EntityManager reference is provided by the managing container (Spring)
 * automatically.
 */
@Service("orderService")
@Transactional
@Repository
public class OrderServiceImpl implements OrderService {

    private EntityManager em;
    
    private final OrderItemImplRepository orderItemRepository;

    private final ProductImplRepository productRepository;

    private final UserRepository userRepository;
    
    @Autowired
    public OrderServiceImpl(ProductImplRepository productRepository,
    		OrderItemImplRepository orderItemRepository, 		
    		UserRepository userRepository) {
    	this.orderItemRepository=orderItemRepository;
    	this.productRepository=productRepository;
    	this.userRepository=userRepository;
    }
    
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
	this.em = em;
    }

    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<OrderItem> findOrderItems(String username) {
	if (username != null) {
	    return em.createQuery("select o from OrderItemImpl o where o.user.username = :username order by o.orderDate")
		    .setParameter("username", username).getResultList();
	} else {
	    return null;
	}
    }

    
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Product> findProducts(ProductCriteria criteria)  {
	String pattern = getSearchPattern(criteria);
	int startIndex = criteria.getPage() * criteria.getPageSize();
	return em
		.createQuery(
			"select p from ProductImpl p where lower(p.name) like :pattern or lower(p.description) like :pattern "
				+ " or lower(p.category.name) like :pattern")
		.setParameter("pattern", pattern).setFirstResult(startIndex).setMaxResults(criteria.getPageSize())
		.getResultList();
    }

    
    @Transactional(readOnly = true)
    public OrderItem createOrderItem(Long productId, String username) {
	ProductImpl product = em.find(ProductImpl.class, productId);
	User user = findUser(username);
	//default units as 1
	OrderItem orderItem = new OrderItemImpl(product, user, 1);

	return orderItem;
    }
    
    //add for test
    
    @Transactional(readOnly = true)
    public OrderItem createOrderItem(Long productId) {
	ProductImpl product = em.find(ProductImpl.class, productId);
	OrderItem orderItem = new OrderItemImpl(product, 1);
	return orderItem;
    }
    
    
    @Transactional
    public void persistOrderItem(OrderItem orderItem) {
	em.persist(orderItem);
    }

    
    @Transactional
    public void cancelOrderItem(Long id) {
    	OrderItem orderItem = em.find(OrderItemImpl.class, id);
	if (orderItem != null) {
	    em.remove(orderItem);
	}
    }

    // helpers

    private String getSearchPattern(ProductCriteria criteria) {
	if (StringUtils.hasText(criteria.getSearchString())) {
	    return "%" + criteria.getSearchString().toLowerCase().replace('*', '%') + "%";
	} else {
	    return "%";
	}
    }

    
    @Transactional
    public void save(OrderItem orderItem) {
    	//downcast for order item implementation
    	OrderItemImpl orderItemImpl =(OrderItemImpl) orderItem;
    	//calculate product order total amount and pass to itemTotal
    	this.calculateTotal(orderItemImpl);
    	//save orderItem
    	orderItemRepository.save(orderItemImpl);
    }
   
    
    @Transactional(readOnly = true)
    public ProductImpl findProductById(Long id) {
    	
    	//ProductImpl foundProduct= productRepository.findById(id);
	return productRepository.findById(id);
    }
    
    
    public User findUser(String username) {
    		return userRepository.findByUsername(username);
    }

	
	public void calculateTotal(OrderItem orderItem) {

	    BigDecimal totalCost  = orderItem.getProduct().getPrice().multiply(
	    		new BigDecimal(orderItem.getQuantity()));    
	    orderItem.setItemTotal(totalCost); 
	}
}
