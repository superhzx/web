/**
 * 
 */
package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.PositionDao;
import cn.rails.iServer.core.entity.Position;
import cn.rails.iServer.core.service.system.PositionService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * <p>Title      : 中国铁道科学研究院[]</p>
 * <p>Description: [部门管理]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : hzx
 * @version      : 1.0
 * @date         ：2017-02-16
 */
@Service
@Transactional
public class PositionServiceImpl implements PositionService{

	@Autowired
	private PositionDao dao;
	
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		return dao.listByPage(pageNo, pageSize, par);
	}

	@Override
	public List<Position> list() {
		return dao.list();
	}

	@Override
	public void save(Position t) {
		dao.save(t);
	}

	@Override
	public void update(Position t) {
		dao.update(t);
	}

	@Override
	public void delete(Position t) {
		dao.delete(t);
	}

	@Override
	public Position queryById(Serializable id) {
		return dao.queryById(id);
	}

	@Override
	public List<Position> queryByCondition(Position t) {
		return dao.queryByCondition(t);
	}
	
	@Override
	public List<Position> queryByCondition(String key, Object value) {
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
	public List<Position> queryByCondition(Paramter par) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(par);
	}

	@Override
	public void deleteById(String pid) {
		// TODO Auto-generated method stub
		Position t = new Position();
		t.setId(pid);
		dao.delete(t);
	}

}
