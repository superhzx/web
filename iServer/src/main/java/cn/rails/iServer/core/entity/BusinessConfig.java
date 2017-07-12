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
 * @date 2017年4月19日
 * @description 
 */
@Entity
@Table(name = "w_business_config")
public class BusinessConfig implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	//主键ID
	private String id;
	//流程配置编码
	private String workflowCode;
	//流程配置名称
	private String workflowName;
	//url地址
	private String url;
	
	@Id
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "workflow_code")
	public String getWorkflowCode() {
		return workflowCode;
	}
	public void setWorkflowCode(String workflowCode) {
		this.workflowCode = workflowCode;
	}
	@Column(name = "workflow_name")
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	@Column(name = "url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public BusinessConfig(String id, String workflowCode,
			String workflowName, String url) {
		this.id = id;
		this.workflowCode = workflowCode;
		this.workflowName = workflowName;
		this.url = url;
	}
	/**
	 * 
	 */
	public BusinessConfig() {
		super();
	}
}
