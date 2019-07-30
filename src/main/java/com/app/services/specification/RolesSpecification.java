package com.app.services.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;

public class RolesSpecification implements Specification<Roles> {
	private SpecSearchCriteria criteria;
	private RoleCategory roleCategory;
	public SpecSearchCriteria getCriteria() {
		return criteria;
	}
	
	public RolesSpecification(SpecSearchCriteria criteria) {
		super();
		this.criteria = criteria;	
	}
	
	public RolesSpecification(SpecSearchCriteria criteria,RoleCategory roleCategory) {
		super();
		this.criteria = criteria;	
		this.roleCategory = roleCategory;
		
	}
	@Override
	public Predicate toPredicate(Root<Roles> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		switch (criteria.getOperation()) {
		case EQUALITY:
			if(roleCategory != null){
				Join rootJoin = root.join("roleCategory");
				return builder.equal(rootJoin.get(criteria.getKey()), criteria.getValue());
			};
			return builder.equal(root.get(criteria.getKey()), criteria.getValue());
		case NEGATION:
			return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
		case GREATER_THAN:
			return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
		case LESS_THAN:
			return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
		case LIKE:
			return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
		case STARTS_WITH:
			return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
		case ENDS_WITH:
			return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
		case CONTAINS:
			return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
		default:
			return null;
		}
	}

}
