package com.example.demo.users;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public UserCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<User> findAllWithFilters(PageOptions pageOptions,SearchCriteria searchCriteria ){
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from((User.class));
        Predicate predicate = getPredicate(searchCriteria,userRoot);
        criteriaQuery.where((predicate));

        setOrder(pageOptions, criteriaQuery, userRoot);

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageOptions.getPageNumber() * pageOptions.getPageSize());
        typedQuery.setMaxResults(pageOptions.getPageSize());

        Pageable pageable = getPageable(pageOptions);

        long count = getCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, count);
    }

    private Predicate getPredicate(SearchCriteria searchCriteria, Root<User> userRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(searchCriteria.getKey())){
            predicates.add(criteriaBuilder.like(userRoot.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(@org.jetbrains.annotations.NotNull PageOptions pageOptions, CriteriaQuery<User> criteriaQuery, Root<User> userRoot) {
        if(pageOptions.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(pageOptions.getSortBy())));
        }else{
            criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(pageOptions.getSortBy())));
        }
    }

    private Pageable getPageable(PageOptions pageOptions) {
        Sort sort = Sort.by(pageOptions.getSortDirection(), pageOptions.getSortBy());
        return PageRequest.of(pageOptions.getPageNumber(), pageOptions.getPageSize(), sort);
    }

    private long getCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> countRoot = countQuery.from(User.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
