package org.webflow.order;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.webflow.domain.CategoryImpl;

@Transactional
public interface CategoryImplRepository extends JpaRepository<CategoryImpl,Long> {
	
	Page<CategoryImpl> findAll(Pageable pageable);
	
	List<CategoryImpl> findAll();
	
	CategoryImpl findByNameContainingIgnoringCase(String name);

}
