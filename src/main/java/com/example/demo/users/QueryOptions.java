package com.example.demo.users;

import java.util.List;

public class QueryOptions {
    PageOptions pageOptions;
    List<?> search;

    public PageOptions getPageOptions() {
        return pageOptions;
    }

    public void setPageOptions(PageOptions pageOptions) {
        this.pageOptions = pageOptions;
    }

    public List<?> getSearch() {
        return search;
    }

    public void setSearch(List<?> search) {
        this.search = search;
    }
}
