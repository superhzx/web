package cn.rails.iServer.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 业务角色实体类
 */

@Entity
@Table(name = "w_business_role")
public class BusinessRole {
    private String id;

    private String systemId;

    private String businessRoleName;

    private Date createDate;

    private String memo;
    //编码
    private String code;
    
    //虚拟字段
    //组织角色ID
    private String orgRoleId;
    //组织角色名称
    private String orgRoleName;
    //业务系统名称
    private String systemName;
    
    private String synFlag;

    @Id
    @Column(name = "ID")
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

    @Column(name = "system_id")
    public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

    @Column(name = "business_role_name")
    public String getBusinessRoleName() {
        return businessRoleName;
    }
	public void setBusinessRoleName(String businessRoleName) {
        this.businessRoleName = businessRoleName == null ? null : businessRoleName.trim();
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
    
    @Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    @Column(name = "syn_flag")
	public String getSynFlag() {
		return synFlag;
	}

	public void setSynFlag(String synFlag) {
		this.synFlag = synFlag;
	}
    
    
    //虚拟字段
	@Transient
	public String getOrgRoleId() {
		return orgRoleId;
	}

	
	public void setOrgRoleId(String orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	@Transient
	public String getOrgRoleName() {
		return orgRoleName;
	}

	public void setOrgRoleName(String orgRoleName) {
		this.orgRoleName = orgRoleName;
	}
	
	@Transient
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
    
    
	public BusinessRole(String id, String systemId, String businessRoleName,
			Date createDate, String memo,String code) {
		super();
		this.id = id;
		this.systemId = systemId;
		this.businessRoleName = businessRoleName;
		this.createDate = createDate;
		this.memo = memo;
		this.code = code;
	}
	public BusinessRole() {
		super();
	}
	
}