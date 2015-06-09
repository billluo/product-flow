package org.webflow.order;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.webflow.domain.CategoryImpl;
import org.webflow.domain.ProductImpl;
import org.webflow.order.ProductCriteria;

public interface ProductImplRepository extends JpaRepository<ProductImpl,Long> {
	
	Page<ProductImpl> findByNameOrDescription(ProductCriteria productCriteria, Pageable pageable);
	
	Page<ProductImpl> findByCategory(CategoryImpl category, Pageable pageable);
	
	List<ProductImpl> findByCategory(CategoryImpl category);
	
	ProductImpl findById(Long id);

}
