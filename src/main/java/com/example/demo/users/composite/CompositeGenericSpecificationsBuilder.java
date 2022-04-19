package com.example.demo.users.composite;

import com.example.demo.users.common.SearchLogic;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Function;

public class CompositeGenericSpecificationsBuilder<U> {

    public CompositeGenericSpecificationsBuilder() {
    }

    public Specification<U> build(CompositeSpecSearchCriteria compositeSpecSearchCriteria,
                                  Function<CompositeSpecSearchCriteria, Specification<U>> converter
    ) {
        if (compositeSpecSearchCriteria.isCriterion()) {
            return converter.apply(compositeSpecSearchCriteria);
        } else {
            return buildComposite(compositeSpecSearchCriteria, converter);
        }
    }

    private Specification<U> buildComposite(CompositeSpecSearchCriteria compositeSpecSearchCriteria,
                                            Function<CompositeSpecSearchCriteria, Specification<U>> converter
    ) {
        SearchLogic searchLogic = compositeSpecSearchCriteria.getLogic().orElse(null);

        Specification<U> specification = null;
        for (CompositeSpecSearchCriteria filter : compositeSpecSearchCriteria.getFilters()) {
            Specification<U> newSpecification;
            if (filter.isCriterion()) {
                newSpecification = converter.apply(filter);
            } else {
                newSpecification = buildComposite(filter, converter);
            }

            if (specification != null) {
                if (searchLogic.equals(SearchLogic.AND)) {
                    newSpecification = specification.and(newSpecification);
                } else if (searchLogic.equals(SearchLogic.OR)) {
                    newSpecification = specification.or(newSpecification);
                }
            }

            specification = newSpecification;
        }

        return specification;
    }
}
