package com.app.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="roles")
public class Roles {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="roleId")
	private Long roleId;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private RoleCategory roleCategory;
	
    
    @Size(min=1, max=32, message="Role Name must be between 1 and 32 characters")
	@Column(name="role_name", nullable=true, length=255)
	private String roleName;

	public Roles(String roleName) {
		this.roleName =  roleName;
	}
	public Roles() {
		super();
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public RoleCategory getRoleCategory() {
		return roleCategory;
	}

	public void setRoleCategory(RoleCategory roleCategory) {
		this.roleCategory = roleCategory;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
}
