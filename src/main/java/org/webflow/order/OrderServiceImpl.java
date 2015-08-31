package org.webflow.order;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * A JPA-based implementation of the Ordering Service. Delegates to a JPA entity manager to 
 * issue data access calls against the backing repository. The EntityManager reference is 
 * provided by the managing container (Spring) automatically.
 * @author bluo
 *
 */
/**
 * @author bluo
 *
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
    private EntityManager em;   
    private final OrderItemImplRepository orderItemRepository;
    private final ProductImplRepository productRepository;
    private final UserRepository userRepository;
    
    /**
     * @param productRepository
     * @param orderItemRepository
     * @param userRepository
     * inject Spring repository beans to service constructor
     */
    @Autowired
    public OrderServiceImpl(ProductImplRepository productRepository,
    		OrderItemImplRepository orderItemRepository, 		
    		UserRepository userRepository) {
    	this.orderItemRepository=orderItemRepository;
    	this.productRepository=productRepository;
    	this.userRepository=userRepository;
    }
    
    /**
     * @param em using @PersistenceContext inject entityManager
     * from Persistent Unit "OrderPU"
     */
    @PersistenceContext(name="OrderPU")
    public void setEntityManager(EntityManager em) {
	this.em = em;
    }

    
    /**
     * This query doesn't paginate the result list
     * @param username
     */
    @Transactional(readOnly = true)
    public List<OrderItemImpl> findOrderItems(String username) {
    		if (username != null) {
//			JPA 2.0 can use generic applied to result list
    			TypedQuery<OrderItemImpl> oQuery = em.createQuery("select o from OrderItemImpl o where "
    					+ "o.user.username = :username order by o.orderDate", OrderItemImpl.class);
    			return oQuery.setParameter("username", username)
    						 .getResultList();
    		} else {
    			return null;
    		}
    }
    
    /**
     * Result pagination-- there are two ways :
     * One way for pagination: use Spring Repository and Page (PageRequest) interface 
     * to pull out result pages
     */ 
    @Transactional(readOnly = true)
	public Page<OrderItemImpl> findOrderItems(String name, Pageable pageable) {
		return orderItemRepository.findByUser(userRepository.findByUsername(name), pageable);
	}
    
    /**
     * Other way for pagination: use JPA-Hibernate function setFirstResult &
     * setMaxResult to pull out result pages
     */
    @Transactional(readOnly = true)
    public List<ProductImpl> findProducts(ProductCriteria criteria)  {
	String pattern = getSearchPattern(criteria);
	int startIndex = criteria.getPage() * criteria.getPageSize();
	TypedQuery<ProductImpl> pQuery = em.createQuery("select p from ProductImpl p "
			+ "where lower(p.name) like :pattern "
			+ "or lower(p.description) like :pattern "
			+ " or lower(p.category.name) like :pattern",ProductImpl.class);
	return pQuery.setParameter("pattern", pattern)
			.setFirstResult(startIndex)
			.setMaxResults(criteria.getPageSize())
			.getResultList();
    }

    
    @Transactional(readOnly = true)
    public OrderItemImpl createOrderItem(Long productId, String username) {
    		ProductImpl product = em.find(ProductImpl.class, productId);
    		User user = findUser(username);
    		//default units as 1
    		OrderItemImpl orderItem = new OrderItemImpl(product, user, 1);

    		return orderItem;
    }
    
    @Transactional(readOnly = true)
    public OrderItem createOrderItem(Long productId) {
    		ProductImpl product = em.find(ProductImpl.class, productId);
    		OrderItem orderItem = new OrderItemImpl(product, 1);
    		return orderItem;
    }  
//    @Transactional
//    	public void persistOrderItem(OrderItem orderItem) {
//    		em.persist(orderItem);
//    }
    @Transactional
    public void cancelOrderItem(Long id) {
    		OrderItem orderItem = em.find(OrderItemImpl.class, id);
    		if (orderItem != null) {
    			em.remove(orderItem);
    		}
    }

    // helpers class
    private String getSearchPattern(ProductCriteria criteria) {
    		if (StringUtils.hasText(criteria.getSearchString())) {
    			return "%" + criteria.getSearchString().toLowerCase().replace('*', '%') + "%";
    		} else {
    			return "%";
    		}
    }
    
    @Transactional
    public void save(OrderItemImpl orderItem) {
    		//downcast for order item implementation
    		OrderItemImpl orderItemImpl =(OrderItemImpl) orderItem;
    		//calculate product order total amount and pass to itemTotal
    		this.calculateTotal(orderItemImpl);
    		//save orderItem
    		orderItemRepository.save(orderItemImpl);
    }
   
    
    @Transactional(readOnly = true)
    public ProductImpl findProductById(Long id) {
    		return productRepository.findById(id);
    }
    
    @Transactional(readOnly = true)
    public User findUser(String username) {
    		return userRepository.findByUsername(username);
    }

    @Transactional
	public void calculateTotal(OrderItemImpl orderItem) {
	    BigDecimal totalCost  = orderItem.getProduct().getPrice().multiply(
	    		new BigDecimal(orderItem.getQuantity()));    
	    orderItem.setItemTotal(totalCost); 
	}
}
