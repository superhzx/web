package cn.rails.iServer.core.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 组织实体类
 */

@Entity
@Table(name = "w_business_org_role")
public class BusinessOrgRole {
	//主键
    private String id;
    
    //业务组织角色名称
    private String orgRoleName;

    //部门编码
    private String departmentCode;

    //创建日期
    private Date createDate;

    //备注
    private String memo;
    
    //部门信息
    private String departmentName;
    
    
    //业务角色
    private String rolename;
    
    //用户ID
    private String userId;
    //用户名称
    private String userName;
    
    //部门ID
    private String departmentId;
    
    //编码
    private String code;
    
    private String synFlag;
    
    //业务角色代码
    private String businessRoleCode;
    //业务角色id
    private String businessRoleId;
    //虚拟字段用户的编码
    private String userCode;
    
    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    @Column(name = "org_role_name")
    public String getOrgRoleName() {
        return orgRoleName;
    }

    public void setOrgRoleName(String orgRoleName) {
        this.orgRoleName = orgRoleName == null ? null : orgRoleName.trim();
    }

    @Column(name = "department_code")
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode == null ? null : departmentCode.trim();
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

    @Transient
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	@Transient
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Transient
	public String getUserId() {
		return userId;
	}

	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Transient
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "department_id")
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

    @Column(name = "syn_flag")
	public String getSynFlag() {
		return synFlag;
	}

	public void setSynFlag(String synFlag) {
		this.synFlag = synFlag;
	}

	@Column(name = "business_role_code")
	public String getBusinessRoleCode() {
		return businessRoleCode;
	}

	public void setBusinessRoleCode(String businessRoleCode) {
		this.businessRoleCode = businessRoleCode;
	}

	@Column(name = "business_role_id")
	public String getBusinessRoleId() {
		return businessRoleId;
	}

	public void setBusinessRoleId(String businessRoleId) {
		this.businessRoleId = businessRoleId;
	}
	
	
	
    
}