package com.example.demo.filtering.composite;

import com.example.demo.filtering.common.PageOptions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositeQueryOptions {
    public PageOptions pageOptions;
    public CompositeSpecSearchCriteria search;
}
