package com.algomart.kibouregistry.util;

import com.algomart.kibouregistry.models.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
    public Predicate toPredicate(Root<T> root, jakarta.persistence.criteria.CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : criteriaList) {
            switch (criteria.getOperation()) {
                case EQUAL:
                    predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case GREATER_THAN:
                    predicates.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
                    break;
                case LESS_THAN:
                    predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
                    break;
                case LIKE:
                    predicates.add(criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                    break;
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}