package org.webflow.domain;

//import java.io.Serializable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * An Order item made by a Customer.
 */
@Entity
public class OrderItemImpl implements OrderItem, Serializable { 

    /**
	 * 
	 */
	private static final long serialVersionUID = 1948937734780257513L;

	private Long id;
  
    private User user;
    
    private ProductImpl product;
    
    private String description;
    
    private int quantity;

    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private Date orderDate;

    private String creditCard;

    private String creditCardName;

    private int creditCardExpiryMonth;

    private int creditCardExpiryYear;

    private CategoryImpl category;
    
    private BigDecimal itemTotal;

    public OrderItemImpl() {
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DAY_OF_MONTH, 1);
	setOrderDate(calendar.getTime());
    }

    public OrderItemImpl(ProductImpl product, int quantity) {
	this();
	this.product = product;
	this.quantity = quantity;
    }
    
    public OrderItemImpl(ProductImpl product, User user, int quantity) {
	this();
	this.product = product;
	this.user = user;
	this.quantity = quantity;
    }
    
    @Transient
    public BigDecimal getTotal() {
	return product.getPrice().multiply(new BigDecimal(getQuantity()));
    }

    public int getQuantity() {
	    return quantity;
    }
    
    public void setQuantity(int quantity) {
    		this.quantity = quantity;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @NotNull
    @Future
    public Date getOrderDate() {
	return orderDate;
    }

    public void setOrderDate(Date datetime) {
	this.orderDate = datetime;
    }

    @ManyToOne
    public ProductImpl getProduct() {
	return product;
    }
   
    public void setProduct(ProductImpl product) {
	this.product = product;
    }

    
    @ManyToOne
    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    @NotEmpty
    public String getCreditCard() {
	return creditCard;
    }

    public void setCreditCard(String creditCard) {
	this.creditCard = creditCard;
    }

    
    public String getDescription() {
	DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	return product == null ? null : product.getName() + ", " + df.format(getOrderDate()) + " to "
		+ df.format(getOrderDate());
    }


    public String getCreditCardName() {
	return creditCardName;
    }

    public void setCreditCardName(String creditCardName) {
	this.creditCardName = creditCardName;
    }

    public int getCreditCardExpiryMonth() {
	return creditCardExpiryMonth;
    }

    public void setCreditCardExpiryMonth(int creditCardExpiryMonth) {
	this.creditCardExpiryMonth = creditCardExpiryMonth;
    }

    public int getCreditCardExpiryYear() {
	return creditCardExpiryYear;
    }

    public void setCreditCardExpiryYear(int creditCardExpiryYear) {
	this.creditCardExpiryYear = creditCardExpiryYear;
    }

    @ManyToOne
    public CategoryImpl getCategory() {
	return category;
    }

    public void setCategory(Category category) {
	this.category = (CategoryImpl) category;
    }

    @SuppressWarnings("unused")
	private Date today() {
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DAY_OF_MONTH, -1);
	return calendar.getTime();
    }

    @Override
    public String toString() {
	return "Product(" + user + "," +  "," + product + "," + description + ")";
    }

	
	public void setDescription(String description) {
		this.description = description;
	}

	
	public BigDecimal getItemTotal() {
		return itemTotal;
	}
	
	
	public void setItemTotal(BigDecimal itemTotal) {
		this.itemTotal=itemTotal;
	}


}
