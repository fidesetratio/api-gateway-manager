package com.app.manager.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name="roles_categories")
public class RoleCategory {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="roleCategoryId")
	private Long roleCategoryId;
	
	@Size(min=1, max=32, message="Role Name must be between 1 and 32 characters")
	@Column(name="category_name", nullable=true, length=255)
	private String categoryName;
	

	@Column(name="category_description", nullable=true, length=255)
	private String categoryDescription;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;

	@OneToMany(mappedBy = "roleCategory", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Roles> roles; 

	
	public RoleCategory() {
		this.roles = new HashSet<Roles>();
		this.creationDateTime = new Date();
	}
	
	 public void addRole(Roles role) {
		 roles.add(role);
		 role.setRoleCategory(this);
	    }
	 
	    public void removeComment(Roles role) {
	    	roles.remove(role);
	        role.setRoleCategory(null);
	    }
	public Long getRoleCategoryId() {
		return roleCategoryId;
	}

	public void setRoleCategoryId(Long roleCategoryId) {
		this.roleCategoryId = roleCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public Date getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}


}
