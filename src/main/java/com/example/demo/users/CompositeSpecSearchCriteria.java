package com.example.demo.users;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

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

    public Optional<String> getKey() {
        return key;
    }

    public void setKey(Optional<String> key) {
        this.key = key;
    }

    public Optional<SearchOperation> getOperation() {
        return operation;
    }

    public void setOperation(Optional<SearchOperation> operation) {
        this.operation = operation;
    }

    public Optional<Object> getValue() {
        return value;
    }

    public void setValue(Optional<Object> value) {
        this.value = value;
    }

    public Optional<SearchLogic> getLogic() {
        return logic;
    }

    public void setLogic(Optional<SearchLogic> logic) {
        this.logic = logic;
    }

    public List<CompositeSpecSearchCriteria> getFilters() {
        return filters;
    }

    public void setFilters(List<CompositeSpecSearchCriteria> filters) {
        this.filters = filters;
    }
}
