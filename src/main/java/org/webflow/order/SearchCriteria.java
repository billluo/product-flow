package org.webflow.order;

public interface SearchCriteria {
    public String getSearchString();
    public void setSearchString(String searchString);

    public int getPageSize();
    public void setPageSize(int pageSize);

    public int getPage();
    public void setPage(int page);
}
