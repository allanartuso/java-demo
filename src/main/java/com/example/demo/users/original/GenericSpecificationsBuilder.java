package com.example.demo.users.original;

import com.example.demo.users.common.SearchOperation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericSpecificationsBuilder<U> {

    private final List<SpecSearchCriteria> params;
    private final ObjectMapper objectMapper;

    public GenericSpecificationsBuilder() {
        this.params = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
    }

    public Specification<U> build(Function<SpecSearchCriteria, Specification<U>> converter) {

        if (params.size() == 0) {
            return null;
        }

        final List<Specification<U>> specs = params.stream()
                                                   .map(converter)
                                                   .collect(Collectors.toCollection(ArrayList::new));

        Specification<U> result = specs.get(0);

        for (int idx = 1; idx < specs.size(); idx++) {
            result = params.get(idx)
                           .isOrPredicate()
                    ? Specification.where(result)
                                   .or(specs.get(idx))
                    : Specification.where(result)
                                   .and(specs.get(idx));
        }

        return result;
    }

    public Specification<U> build(List<?> postFixedExprStack,
                                  Function<SpecSearchCriteria, Specification<U>> converter) {

        Deque<Specification<U>> specStack = new LinkedList<>();

        Collections.reverse(postFixedExprStack);

        while (!postFixedExprStack.isEmpty()) {
            Object mayBeOperand = postFixedExprStack.remove(0);

            if (!(mayBeOperand instanceof String)) {
                SpecSearchCriteria specSearchCriteria = objectMapper
                        .convertValue(mayBeOperand, SpecSearchCriteria.class);
                specStack.push(converter.apply(specSearchCriteria));
            } else {
                Specification<U> operand1 = specStack.pop();
                Specification<U> operand2 = specStack.pop();
                if (mayBeOperand.equals(SearchOperation.AND_OPERATOR)) {
                    specStack.push(Specification.where(operand1)
                                                .and(operand2));
                } else if (mayBeOperand.equals(SearchOperation.OR_OPERATOR)) {
                    specStack.push(Specification.where(operand1)
                                                .or(operand2));
                }
            }
        }
        return specStack.pop();
    }
}
