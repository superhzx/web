package cn.rails.iServer.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author hzx
 * @date 2017年4月12日
 * @description 业务角色组织角色中间表实体类
 */

@Entity
@Table(name = "w_business_role_org_role")
public class BusinessRoleOrgRole {
    private String id;

    private String businessRoleId;

    private String businessOrgRoleId;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "business_role_id")
    public String getBusinessRoleId() {
        return businessRoleId;
    }

    public void setBusinessRoleId(String businessRoleId) {
        this.businessRoleId = businessRoleId;
    }

    @Column(name = "business_org_role_id")
    public String getBusinessOrgRoleId() {
        return businessOrgRoleId;
    }

    public void setBusinessOrgRoleId(String businessOrgRoleId) {
        this.businessOrgRoleId = businessOrgRoleId;
    }
}