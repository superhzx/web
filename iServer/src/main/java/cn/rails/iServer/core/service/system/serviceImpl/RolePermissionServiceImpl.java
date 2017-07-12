package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.RolePermissionDao;
import cn.rails.iServer.core.entity.RolePermission;
import cn.rails.iServer.core.service.system.RolePermissionService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;
/**
 * @author wl
 * @date 2017年3月23日
 * @description 角色权限
 */
@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService{
	@Autowired
	private RolePermissionDao dao;
	
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		return dao.listByPage(pageNo, pageSize, par);
	}

	public List<RolePermission> list() {
		return dao.list();
	}

	public void save(RolePermission t) {
		dao.save(t);
	}

	public void update(RolePermission t) {
		dao.update(t);
	}

	public void delete(RolePermission t) {
		dao.delete(t);
	}

	public RolePermission queryById(Serializable id) {
		return dao.queryById(id);
	}

	public List<RolePermission> queryByCondition(RolePermission t) {
		return dao.queryByCondition(t);
	}
	public void deleteByCondition(String key, Object value) {
		dao.deleteByCondition(key, value);
		
	}
	public void saveOrUpdate(RolePermission t) {
		// TODO Auto-generated method stub
		dao.saveOrUpdate(t);
	}

	@Override
	public List<RolePermission> queryByCondition(String key, Object value) {
		return dao.queryByCondition(key, value);
	}
}
