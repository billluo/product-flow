package org.webflow.domain;

import java.math.BigDecimal;
import java.util.Date;

public interface Order {

	public BigDecimal getCostTotal() ;
	public void setCostTotal(BigDecimal itemTotal) ;

    public int getQuantity() ;
    public void setQuantity(int quantity) ;

    public Long getId() ;
    public void setId(Long id) ;

    public Date getOrderDate() ;
    public void setOrderDate(Date datetime) ;

    public User getUser() ;
    public void setUser(User user) ;

    public String getCreditCard() ;
    public void setCreditCard(String creditCard) ;

    public String getDescription() ;
    public void setDescription(String description) ;
}
