package com.example.demo.users.composite;

import com.example.demo.users.common.PageOptions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompositeQueryOptions {
    public PageOptions pageOptions;
    public CompositeSpecSearchCriteria search;
}
