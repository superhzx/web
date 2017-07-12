package cn.rails.iServer.core.service.system.serviceImpl;
import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.DepartmentDao;
import cn.rails.iServer.core.entity.Department;
import cn.rails.iServer.core.service.system.DepartmentService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * 
 * @author hzx
 * @date 2017年3月22日
 * @description 部门实现层
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentDao dao;
	
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		return dao.listByPage(pageNo, pageSize, par);
	}

	@Override
	public List<Department> list() {
		return dao.list();
	}

	@Override
	public void save(Department t) {
		dao.save(t);
	}

	@Override
	public void update(Department t) {
		dao.update(t);
	}

	@Override
	public void delete(Department t) {
		dao.delete(t);
	}

	@Override
	public Department queryById(Serializable id) {
		return dao.queryById(id);
	}

	@Override
	public List<Department> queryByCondition(Department t) {
		return dao.queryByCondition(t);
	}
	
	@Override
	public List<Department> queryByCondition(String key, Object value) {
		return dao.queryByCondition(key, value);
	}
	@Override
	public List<Object> getdeptIds(String departmentId,String orders) {
		return dao.getdeptIds(departmentId, orders);
	}
	@Override
	public List<Object> mysqlgetdeptIds(String departmentId,String orders) {
		return dao.MySQLgetdeptIds(departmentId, orders);
	}
	@Override
	public List<Department> queryByCondition(Paramter par) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(par);
	}

	@Override
	public Department queryByCode(String code) {
		// TODO Auto-generated method stub
		Department d = dao.queryByCode(code);
		String suoCode = d.getParentCode();
		boolean flag = true;
		
		while(flag){
			if(!suoCode.equals("10000001")){
				d = dao.queryByCode(suoCode);
				if(d!=null){
					suoCode = d.getParentCode();
				}else{
					flag=false;
				}
			}else{
				flag=false;
			}
		}
		
		return d;
	}

	@Override
	public String getDepartCode(String code) {
		// TODO Auto-generated method stub
		List<Department> departmentList = dao.queryByParentCode(code);
		
		String suoCode = departmentList.get(0).getCode();
		boolean flag = true;
		
		while(flag){
			if(departmentList!=null){
				code+=","+suoCode;
				departmentList = dao.queryByParentCode(suoCode);
				suoCode = departmentList.get(0).getCode();
			}else{
				flag=false;
			}
		}
		
		return code;
	}
}
