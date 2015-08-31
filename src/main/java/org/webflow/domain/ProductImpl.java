package org.webflow.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.webflow.order.CategoryImplRepository;

/**
 * Implementation of a product where users may order.
 * @author bluo
 */
@Entity
@Table(name ="Product")
public class ProductImpl implements Product,Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3656010089485790528L;

	private Long id;
    private String name;
    private String description;
    
    @Autowired
    CategoryImplRepository categoryRepository;
    	
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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
 
    @ManyToOne(fetch= FetchType.LAZY)
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
		ProductImpl other = (ProductImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
}
