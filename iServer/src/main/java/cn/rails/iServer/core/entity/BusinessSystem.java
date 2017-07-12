/**
 * 
 */
package cn.rails.iServer.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dengjm
 * @date 2017年4月10日
 * @description 
 */
@Entity
@Table(name = "w_business_system")
public class BusinessSystem implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//主键ID
	private String id;
	//业务系统名称
	private String businessSystemName;
	//业务系统分类
	private Integer businessSystemIdentity;
	//创建日期
	private Date createDate;
	//备注
	private String memo;
	
	@Id
    @Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    @Column(name = "business_system_name")
	public String getBusinessSystemName() {
		return businessSystemName;
	}
	public void setBusinessSystemName(String businessSystemName) {
		this.businessSystemName = businessSystemName;
	}
    @Column(name = "business_system_identity")
	public Integer getBusinessSystemIdentity() {
		return businessSystemIdentity;
	}
	public void setBusinessSystemIdentity(Integer businessSystemIdentity) {
		this.businessSystemIdentity = businessSystemIdentity;
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
		this.memo = memo;
	}
	/**
	 * @param id
	 * @param businessSystemName
	 * @param businessSystemIdentity
	 * @param createDate
	 * @param memo
	 */
	public BusinessSystem(String id, String businessSystemName,
			Integer businessSystemIdentity, Date createDate, String memo) {
		this.id = id;
		this.businessSystemName = businessSystemName;
		this.businessSystemIdentity = businessSystemIdentity;
		this.createDate = createDate;
		this.memo = memo;
	}
	/**
	 * 
	 */
	public BusinessSystem() {
		super();
	}
	
}
