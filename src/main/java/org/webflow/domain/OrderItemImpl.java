package org.webflow.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * An Order item made by a Customer.
 * @author bluo
 */
@Entity
@Table(name = "OrderItem")
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
    
    //multiple constructors
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
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
    		return id;
    }

    public void setId(Long id) {
    		this.id = id;
    }
    
    //calculate the total value for order item
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

    //date time format conversion with Oracle database
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

    @ManyToOne(fetch= FetchType.LAZY)
    public ProductImpl getProduct() {
    		return product;
    }
   
    public void setProduct(ProductImpl product) {
    		this.product = product;
    }
    
    @ManyToOne(fetch= FetchType.LAZY)
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

    @ManyToOne(fetch= FetchType.LAZY)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItemImpl other = (OrderItemImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
