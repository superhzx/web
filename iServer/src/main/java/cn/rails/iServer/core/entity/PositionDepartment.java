package cn.rails.iServer.core.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * @author hzx
 * @date 2017年3月23日
 * @description 岗位部门关联表
 */
@Entity
@Table(name = "S_POSITION_DEPARTMENT")
public class PositionDepartment {
	private String id;
	
    private String positionId;

    private String departmentId;

    private Position positions;
    
    private Department departments;
    
    
    
    @Id
	@Column(name = "id")
    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	@Column(name = "position_id")
    public String getPositionId() {
        return positionId;
    }

    
    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
    }

    @Column(name = "department_id")
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }


   
    public PositionDepartment() {
		super();
	}

	
	
    public PositionDepartment(String id, String positionId,
			String departmentId, Position positions, Department departments) {
		super();
		this.id = id;
		this.positionId = positionId;
		this.departmentId = departmentId;
		this.positions = positions;
		this.departments = departments;
	}


	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="position_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Position getPositions() {
		return positions;
	}


	public void setPositions(Position positions) {
		this.positions = positions;
	}


	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="department_id",insertable=false,updatable=false)
	@NotFound(action=NotFoundAction.IGNORE)
	public Department getDepartments() {
		return departments;
	}


	public void setDepartments(Department departments) {
		this.departments = departments;
	}
    
	
    
    
}