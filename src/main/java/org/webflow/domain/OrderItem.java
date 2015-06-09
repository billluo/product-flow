package org.webflow.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * An Order item made by a Customer.
 */

public interface OrderItem {

	public BigDecimal getItemTotal() ;
	public void setItemTotal(BigDecimal itemTotal) ;

    public int getQuantity() ;
    public void setQuantity(int quantity) ;

    public Long getId() ;

    public void setId(Long id) ;

    public Date getOrderDate() ;
    public void setOrderDate(Date datetime) ;

    /**
     * @return
     */
    public ProductImpl getProduct() ;//
    public void setProduct(ProductImpl product);

    public User getUser() ;
    public void setUser(User user) ;

    public String getCreditCard() ;
    public void setCreditCard(String creditCard) ;

    public String getDescription() ;
    public void setDescription(String description) ;

    public String getCreditCardName() ;
    public void setCreditCardName(String creditCardName) ;

    public int getCreditCardExpiryMonth() ;
    public void setCreditCardExpiryMonth(int creditCardExpiryMonth) ;

    public int getCreditCardExpiryYear() ;
    public void setCreditCardExpiryYear(int creditCardExpiryYear) ;

    /**
     * @return
     */
    public CategoryImpl getCategory() ;
    public void setCategory(Category category) ;

    public String toString() ;

}
