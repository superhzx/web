package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.PermissionDao;
import cn.rails.iServer.core.entity.Permission;
import cn.rails.iServer.core.service.system.PermissionService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author wl
 * @date 2017年3月22日
 * @description 权限
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionDao dao;
	/**
	 * 根据人员ID获取菜单列表
	 * @author sunyh
	 */
	@Override
	public List<Permission> getMenuListByUserId(String userId,String parentId) {
		return dao.getMenuListByUserId(userId,parentId);
	}
	
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		return dao.listByPage(pageNo, pageSize, par);
	}

	@Override
	public List<Permission> list() {
		return dao.list();
	}

	public void save(Permission t) {
		dao.save(t);
	}

	@Override
	public void update(Permission t) {
		dao.update(t);
	}

	@Override
	public void saveOrUpdate(Permission t) {
		dao.saveOrUpdate(t);
	}

	@Override
	public void delete(Permission t) {
		dao.delete(t);
	}

	@Override
	public Permission queryById(Serializable id) {
		return dao.queryById(id);
	}

	@Override
	public List<Permission> queryByCondition(String key, Object value) {
		return dao.queryByCondition(key, value);
	}

	@Override
	public List<Permission> queryByCondition(Permission t) {
		return dao.queryByCondition(t);
	}
}
