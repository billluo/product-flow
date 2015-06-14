package org.webflow.domain;

import java.io.Serializable;
import java.math.BigDecimal;










//import javax.persistence.Access;
//import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.webflow.order.CategoryImplRepository;

//import org.hibernate.validator.constraints.NotEmpty;

/**
 * Implementation of a product where users may order.
 */
@Entity
@Table(name ="Product")
public class ProductImpl implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3656010089485790528L;

	private Long id;

    private String name;

    private String description;
    
    @Autowired
    CategoryImplRepository categoryRepository;

	@ManyToOne
    private CategoryImpl category;

    private BigDecimal price;
    
    public ProductImpl(){};
    
    public ProductImpl(CategoryImpl category,String name,String description) {
    	this.name=name;
    	this.description=description;
    	this.category=category;
    }
    
    public ProductImpl(String categoryId,String name,String description) {
    	this.name=name;
    	this.description=description;
    	
    	Long categoryIdLong=Long.parseLong(categoryId);
    	this.category=categoryRepository.findById(categoryIdLong);
    }
    
    @Id
    @GeneratedValue
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
 
    @ManyToOne
    public CategoryImpl getCategory() {
	return category;
    }

    public void setCategory(CategoryImpl category) {
	this.category = category;
    }
    
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Column(precision = 6, scale = 2)
    public BigDecimal getPrice() {
	return price;
    }

    public void setPrice(BigDecimal price) {
	this.price = price;
    }

    @Override
    public String toString() {
	return "Product(" + name + "," + description + "," + category +" )";
    }
}
