package cn.rails.iServer.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author wangqi
 * @date 2017年3月23日
 * @description 用户岗位
 */

@Entity
@Table(name = "S_USER_POSITION")
public class UserPosition implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	//人员ID
	private String userId; 
	//角色ID
	private String positionId; 
	//是否主岗
	private String isMain; 
 
	public UserPosition() {
		super();
	}
	
	public UserPosition(String positionId,String userId) {
		this.positionId = positionId;
		this.userId=userId;
	}
	
	public UserPosition(String id,String positionId,String userId) {
		this.id = id;
		this.positionId = positionId;
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
	@Column(name="POSITION_ID")
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	@Column(name="IS_MAIN")
	public String getIsMain() {
		return isMain;
	}

	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}
}