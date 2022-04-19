package com.example.demo.users.original;

import com.example.demo.users.common.PageOptions;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QueryOptions {
    public PageOptions pageOptions;
    public List<?> search;
}
