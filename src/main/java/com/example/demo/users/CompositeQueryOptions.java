package com.example.demo.users;

import java.util.List;

public class CompositeQueryOptions {
    PageOptions pageOptions;
    CompositeSpecSearchCriteria search;

    public PageOptions getPageOptions() {
        return pageOptions;
    }

    public void setPageOptions(PageOptions pageOptions) {
        this.pageOptions = pageOptions;
    }

    public CompositeSpecSearchCriteria getSearch() {
        return search;
    }

    public void setSearch(CompositeSpecSearchCriteria search) {
        this.search = search;
    }
}
