package com.example.demo.users.composite;

import com.example.demo.users.common.SearchOperation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CompositeGenericSpecification<T> implements Specification<T> {

    private CompositeSpecSearchCriteria criteria;

    public CompositeGenericSpecification(final CompositeSpecSearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public CompositeSpecSearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
        SearchOperation operation = criteria.getOperation().orElse(null);
        String key = criteria.getKey().orElse(null);
        Object value = criteria.getValue().orElse(null);

        switch (operation) {
            case EQUALITY:
                return builder.equal(root.get(key), value);
            case NEGATION:
                return builder.notEqual(root.get(key), value);
            case GREATER_THAN:
                return builder.greaterThan(root.get(key), value.toString());
            case LESS_THAN:
                return builder.lessThan(root.get(key), value.toString());
            case LIKE:
                return builder.like(root.get(key), value.toString());
            case STARTS_WITH:
                return builder.like(root.get(key), value + "%");
            case ENDS_WITH:
                return builder.like(root.get(key), "%" + value);
            case CONTAINS:
                return builder.like(root.get(key), "%" + value + "%");
            default:
                return null;
        }
    }
}
