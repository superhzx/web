package cn.rails.iServer.core.service.system;


import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.PositionDepartment;

/**
 * 
 * @author hzx
 * @date 2017年3月23日
 * @description 部门岗位service
 */
public interface PositionDepartmentService extends IBaseService<PositionDepartment>{
	//根据用户ID更新用户部门表的数据
	public void updateByUserId(String userId,String departmentId);
	public void deleteByUserId(String userId) ;
	public void update(PositionDepartment t) ;
	//根据人员ID获取部门ID
	public PositionDepartment getDepartmentIdByPosition(String positionId);
	//修改中间表数据
	public void updatepositiondepartment(String pdid, String departmentId);
}
