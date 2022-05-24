package com.example.demo.filtering.composite;

import com.example.demo.filtering.common.SearchLogic;
import com.example.demo.filtering.common.SearchOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

@Getter
@Setter
public class CompositeSpecSearchCriteria {
    private Optional<String> key = empty();
    private Optional<SearchOperation> operation = empty();
    private Optional<Object> value = empty();
    private Optional<SearchLogic> logic = empty();
    private List<CompositeSpecSearchCriteria> filters = emptyList();

    public CompositeSpecSearchCriteria() {
    }

    public CompositeSpecSearchCriteria(Optional<SearchLogic> logic, List<CompositeSpecSearchCriteria> filters) {
        this.logic = logic;
        this.filters = filters;
    }

    public CompositeSpecSearchCriteria(Optional<String> key, Optional<SearchOperation> operation,
                                       Optional<Object> value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public boolean isCriterion() {
        return this.key.isPresent();
    }

    public boolean isComposite() {
        return this.logic.isPresent();
    }
}
