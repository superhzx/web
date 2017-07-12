/**
 * 
 */
package cn.rails.iServer.core.dao.system;

import java.util.List;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.utils.page.Paramter;

/**
 * 
 * @author hzx
 * @date 2017年3月22日
 * @description 部门DAO层
 */
public interface DepartmentDao extends IBaseDao<Department>{
	public List<Object> getdeptIds(String departmentId,String orders);
	
	//根据paramer查询数据
	public List<Department> queryByCondition(Paramter par);

	public List<Object> MySQLgetdeptIds(String departmentId, String orders);

	public Department queryByCode(String code);

	List<Department> queryByParentCode(String code);

}
