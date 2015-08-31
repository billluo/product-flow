package org.webflow.order;

import java.io.Serializable;

/**
 * @author bluo
 *This class implement the SearchCriteria
 */
public class ProductCriteria implements SearchCriteria,Serializable {
	
    private static final long serialVersionUID = 1L;

    /**
     * The user-provided search criteria for finding Product.
     */
    private String searchString;

    /**
     * The maximum page size of the Product result list
     */
    private int pageSize;

    /**
     * The current page of the Product result list.
     */
    private int page;

    public String getSearchString() {
    		return searchString;
    }

    public void setSearchString(String searchString) {
    		this.searchString = searchString;
    }

    public int getPageSize() {
    		return pageSize;
    }

    public void setPageSize(int pageSize) {
    		this.pageSize = pageSize;
    }

    public int getPage() {
    		return page;
    }

    public void setPage(int page) {
    		this.page = page;
    }
}
