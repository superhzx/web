package cn.rails.iServer.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author wangqi
 * @date 2017年3月23日
 * @description 用户类
 */
@Entity
@Table(name = "S_USER")
public class User implements java.io.Serializable {
 
	private static final long serialVersionUID = 1L;
	//主键ID
	private String id;
	//用户编码
	private String code;
	//用户名称
	private String name;
	//性别
	private String sex;
	//用户全名
	private String aliasName;
	//密码
	private String pwd;
	//手机号
	private String mobile;
	//固定电话
	private String phone;
	//启用时间
	private Date availableDate;
	//失效日期
	private Date expireDate;
	//创建用户id
	private String createUserId;
	//排序
	private Integer sortNumber;
	//备注
	private String memo;
	
	private String positionId;
	
	private String positionName;
	
	private String isMain;
	
	private String roleId;
	
	private String roleName;
	
	private String synFlag;
	
	//邮箱
	private String email;
	//身份证号
	private String cardNo;
	//ERP编码
	private String erpCode;
	
	
	//所名称
	private String magnitudeName;
	//所编码
	private String magnitudeCode;
	//部门名称
	private String departmentName;
	//部门编码
	private String departmentCode;
	
	@Transient
	public String getMagnitudeCode() {
		return magnitudeCode;
	}
	public void setMagnitudeCode(String magnitudeCode) {
		this.magnitudeCode = magnitudeCode;
	}
	
	@Transient
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	@Transient
	public String getMagnitudeName() {
		return magnitudeName;
	}
	public void setMagnitudeName(String magnitudeName) {
		this.magnitudeName = magnitudeName;
	}
	
	@Transient
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	@Transient
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Transient
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Transient
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	@Transient
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public User(){
		super();
	}
	public User(String id,String code,String name,String aliasName,String pwd,String mobile,String phone,Date availableDate,Date expireDate,String createUserId,Integer sortNumber,String memo,String erpCode){
		this.id = id;
		this.code = code;
		this.name = name;
		this.aliasName = aliasName;
		this.pwd = pwd;
		this.mobile = mobile;
		this.phone = phone;
		this.availableDate = availableDate;
		this.expireDate = expireDate;
		this.createUserId = createUserId;
		this.sortNumber = sortNumber;
		this.memo = memo;
		this.erpCode = erpCode;
	}
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name = "alias_name")
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	@Column(name = "pwd")
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "available_date")
	public Date getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}
	@Column(name = "expire_date")
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	@Column(name = "create_user_id")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name = "sort_number")
	public Integer getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}
	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
    @Column(name = "syn_flag")
	public String getSynFlag() {
		return synFlag;
	}

	public void setSynFlag(String synFlag) {
		this.synFlag = synFlag;
	}
	
	@Transient
	public String getIsMain() {
		return isMain;
	}
	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "card_number")
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	@Column(name = "erp_code")
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}
	
	
}