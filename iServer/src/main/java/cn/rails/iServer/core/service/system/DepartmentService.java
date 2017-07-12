package cn.rails.iServer.core.service.system;
import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.utils.page.Paramter;

/**
 * 
 * @author hzx
 * @date 2017年3月22日
 * @description 部门的实现层
 */
public interface DepartmentService extends IBaseService<Department>{
	public List<Department> queryByCondition(String key,Object value);
	public List<Object> getdeptIds(String DepartsmentId,String orders);
	
	//根据paramer查询数据
	public List<Department> queryByCondition(Paramter par);
	public Department queryByCode(String code);
	public List<Object> mysqlgetdeptIds(String DepartmentId, String orders);
	String getDepartCode(String code);

}
