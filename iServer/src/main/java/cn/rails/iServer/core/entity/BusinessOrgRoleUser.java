package cn.rails.iServer.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author hzx
 * @date 2017年4月11日
 * @description 业务角色用户中间表
 */

@Entity
@Table(name = "w_business_org_role_user")
public class BusinessOrgRoleUser {
	//主键
    private String id;

    //组织角色ID
    private String orgRoleId;

    //用户编码
    private String userId;

    @Id
    @Column(name = "id")
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "org_role_id")
    public String getOrgRoleId() {
        return orgRoleId;
    }

    public void setOrgRoleId(String orgRoleId) {
        this.orgRoleId = orgRoleId;
    }
    
    @Column(name = "user_id")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
   
}