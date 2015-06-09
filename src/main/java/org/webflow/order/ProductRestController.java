package org.webflow.order;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.webflow.domain.ProductImpl;

@RestController
@RequestMapping("/{categoryId}/products")
public class ProductRestController {

	private final ProductImplRepository productRepository;

	private final CategoryImplRepository categoryRepository;

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String categoryId, @RequestBody ProductImpl input) {
		this.validateCategory(categoryId);
		
		ProductImpl result = productRepository.save(new ProductImpl(input.getCategory(),
				input.getName(), input.getDescription()));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri());
		return new ResponseEntity<Object>(null, httpHeaders, HttpStatus.CREATED);
		
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	ProductImpl getProduct(@PathVariable String categoryId, @PathVariable Long productId) {
		//this.validateCategory(categoryId);
		return this.productRepository.findById(productId);
	}

	@RequestMapping(method = RequestMethod.GET)
	List<ProductImpl> getProducts(@PathVariable String categoryId) {
		this.validateCategory(categoryId);
		return this.productRepository.findByCategory(categoryRepository.findByNameContainingIgnoringCase(categoryId));
	}

	@Autowired
	ProductRestController(ProductImplRepository productRepository,
			CategoryImplRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	private void validateCategory(String categoryId) {

		if (this.categoryRepository.findByNameContainingIgnoringCase(categoryId)==null){
			 throw new  ProductNotFoundException(categoryId);
		}		
	}	
}
	

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String categoryId) {
		super("could not find Category '" + categoryId + "'.");
	}
	
}
