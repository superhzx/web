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
 * @description
 */
@Entity
@Table(name = "w_comment")
public class Comment {
	private static final long serialVersionUID = 1L;
	//主键
	private String id;
	//流程ID
	private String intanceId;
	//审批人
	private String approvalUser;
	//审批日期
	private Date approvalDate;
	//审批状态
	private String approvalState;
	//审批意见
	private String approvalOpinion;
	//电子签章
	private String signatureId;
	//电子签章路径
	private String signatureImage;
	
	//虚拟字段 审批人姓名
	private String approvalUserName;
	
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "INTANCE_ID")
	public String getIntanceId() {
		return intanceId;
	}
	public void setIntanceId(String intanceId) {
		this.intanceId = intanceId;
	}
	
	@Column(name = "APPROVAL_USER")
	public String getApprovalUser() {
		return approvalUser;
	}
	public void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}
	
	@Column(name = "APPROVAL_DATE")
	public Date getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}
	@Column(name = "APPROVAL_STATE")
	public String getApprovalState() {
		return approvalState;
	}
	public void setApprovalState(String approvalState) {
		this.approvalState = approvalState;
	}
	
	@Column(name = "APPROVAL_OPINION")
	public String getApprovalOpinion() {
		return approvalOpinion;
	}
	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
	
	@Column(name = "SIGNATURE_ID")
	public String getSignatureId() {
		return signatureId;
	}
	
	public void setSignatureId(String signatureId) {
		this.signatureId = signatureId;
	}
	
	@Column(name = "SIGNATURE_IMAGE")
	public String getSignatureImage() {
		return signatureImage;
	}
	
	public void setSignatureImage(String signatureImage) {
		this.signatureImage = signatureImage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Comment() {
	}
	
	@Transient
	public String getApprovalUserName() {
		return approvalUserName;
	}
	public void setApprovalUserName(String approvalUserName) {
		this.approvalUserName = approvalUserName;
	}
	
	
	
	
}
