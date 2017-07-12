package cn.rails.iServer.core.service.system.serviceImpl;
import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.PositionDepartmentDao;
import cn.rails.iServer.core.entity.PositionDepartment;
import cn.rails.iServer.core.service.system.PositionDepartmentService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

@Service
@Transactional
public class PositionDepartmentServiceImpl implements PositionDepartmentService{
	@Autowired
	private PositionDepartmentDao dao ;
	public void delete(PositionDepartment t) {
		dao.delete(t);
	}

	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		return dao.listByPage(pageNo, pageSize, par);
	}

	public void save(PositionDepartment t) {
		dao.save(t);
	}

	public void update(PositionDepartment t) {
		dao.update(t);
	}

	@Override
	public List<PositionDepartment> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public PositionDepartment queryById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}

	@Override
	public List<PositionDepartment> queryByCondition(PositionDepartment t) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(t);
	}

	@Override
	public void updateByUserId(String userId, String departmentId) {
		dao.updateByUserId(userId,departmentId);
	}

	@Override
	public void deleteByUserId(String userId) {
		dao.deleteByUserId(userId);
	}

	@Override
	public PositionDepartment getDepartmentIdByPosition(String positionId) {
		// TODO Auto-generated method stub
		return dao.getDepartmentIdByPosition(positionId);
	}

	@Override
	public void updatepositiondepartment(String pdid, String departmentId) {
		// TODO Auto-generated method stub
		/*PositionDepartment t = new PositionDepartment();
		t.setId(pdid);
		t.setDepartmentId(departmentId);
*/		dao.updatepositiondepartment(pdid,departmentId);
	}

}
