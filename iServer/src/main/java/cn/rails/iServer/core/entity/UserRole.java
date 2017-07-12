package cn.rails.iServer.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author wangqi
 * @date 2017年3月23日
 * @description 用户角色
 */
@Entity
@Table(name = "S_USER_ROLE")
public class UserRole implements java.io.Serializable {
 
	private static final long serialVersionUID = 1L;
	private String id;
	//人员ID
	private String userId; 
	//角色ID
	private String roleId; 
 
	public UserRole() {
		super();
	}
	
	public UserRole(String id,String roleId,String userId) {
		this.id = id;
		this.roleId = roleId;
		this.userId=userId;
	}
	
	@Id
	@Column(name="ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	//人员ID
	@Column(name="USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	//角色ID
	@Column(name="ROLE_ID")
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}