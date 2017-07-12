package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.RoleDao;
import cn.rails.iServer.core.entity.Role;
import cn.rails.iServer.core.service.system.RoleService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * 
 * @author hzx
 * @date 2017年3月27日
 * @description 角色业务层
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao dao;

	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		return dao.listByPage(pageNo, pageSize, par);
	}

	public List<Role> list() {
		return dao.list();
	}

	public void save(Role t) {
		dao.save(t);
	}

	public void update(Role t) {
		dao.update(t);
	}

	public void delete(Role t) {
		dao.delete(t);
	}

	public Role queryById(Serializable id) {
		return dao.queryById(id);
	}

	public List<Role> queryByCondition(Role t) {
		return dao.queryByCondition(t);
	}

	public void saveOrUpdate(Role t) {
		// TODO Auto-generated method stub
		dao.saveOrUpdate(t);
	}

	@Override
	public List<Role> queryByCondition(String key, Object value) {
		return dao.queryByCondition(key, value);
	}

}
