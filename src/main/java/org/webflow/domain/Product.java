package org.webflow.domain;

import java.math.BigDecimal;

public interface Product {

    public Long getId() ;

    public void setId(Long id);

    public String getName() ;

    public void setName(String name);
 
    public CategoryImpl getCategory() ;

    public void setCategory(CategoryImpl category) ;
    
    public String getDescription() ;

    public void setDescription(String description) ;

    public BigDecimal getPrice() ;

    public void setPrice(BigDecimal price) ;
    
}
