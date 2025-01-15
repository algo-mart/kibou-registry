package com.algomart.kibouregistry.util;
import com.algomart.kibouregistry.models.SearchCriteria;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class GenericSpecification<T> implements Specification<T> {
    private final List<SearchCriteria> criteriaList;
    public GenericSpecification() {
        this.criteriaList = new ArrayList<>();
    }
    public void add(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            String[] keys = criteria.getKey().split("\\.");
            Path<?> expression = root.get(keys[0]);

            for (int i = 1; i < keys.length; i++) {
                expression = expression.get(keys[i]);
            }

            switch (criteria.getOperation()) {
                case GREATER_THAN:
                    if (Comparable.class.isAssignableFrom(expression.getJavaType())) {
                        predicates.add(builder.greaterThan((Expression<? extends Comparable>) expression, (Comparable) criteria.getValue()));
                    } else {
                        throw new IllegalArgumentException("Attribute is not Comparable for GREATER_THAN operation: " + criteria.getKey());
                    }
                    break;
                case LESS_THAN:
                    if (Comparable.class.isAssignableFrom(expression.getJavaType())) {
                        predicates.add(builder.lessThan((Expression<? extends Comparable>) expression, (Comparable) criteria.getValue()));
                    } else {
                        throw new IllegalArgumentException("Attribute is not Comparable for LESS_THAN operation: " + criteria.getKey());
                    }
                    break;
                case EQUAL:
                    predicates.add(builder.equal(expression, criteria.getValue()));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported operation: " + criteria.getOperation());
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}