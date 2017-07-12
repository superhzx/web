/**
 * 
 */
package cn.rails.iServer.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dengjm
 * @date 2017年6月5日
 * @description 
 */
@Entity
@Table(name = "d_fee_class")
public class FeeClass implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//主键ID
	private String id;
	//费用类型编码
	private String code;
	//费用类型名称
	private String name;
	//父节点id
	private String parentId;
	// 排序
	private int sortNumber; 
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
	@Column(name = "parent_id")
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	@Column(name = "sort_number")
	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
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
	 * @param code
	 * @param name
	 * @param parentId
	 * @param sortNumber
	 * @param memo
	 */
	public FeeClass(String id, String code, String name, String parentId,
			int sortNumber, String memo) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.parentId = parentId;
		this.sortNumber = sortNumber;
		this.memo = memo;
	}
	/**
	 * 
	 */
	public FeeClass() {
		super();
	}
	
	
}
