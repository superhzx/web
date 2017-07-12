package cn.rails.iServer.core.service.system.serviceImpl;
import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.OrgRoleUserDao;
import cn.rails.iServer.core.dao.system.RoleOrgRoleDao;
import cn.rails.iServer.core.dao.system.UserRoleDao;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.BusinessRoleOrgRole;
import cn.rails.iServer.core.entity.UserRole;
import cn.rails.iServer.core.service.system.OrgRoleUserService;
import cn.rails.iServer.core.service.system.RoleOrgRoleService;
import cn.rails.iServer.core.service.system.UserRoleService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * 
 * @author hzx
 * @date 2017年4月11日
 * @description  组织角色和用户中间表
 */

@Service
@Transactional
public class RoleOrgRoleServiceImpl implements RoleOrgRoleService{

	@Autowired
	private RoleOrgRoleDao dao;
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		// TODO Auto-generated method stub
		return dao.listByPage(pageNo, pageSize, par);
	}

	@Override
	public List<BusinessRoleOrgRole> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public void save(BusinessRoleOrgRole t) {
		// TODO Auto-generated method stub
		dao.save(t);
	}

	@Override
	public void update(BusinessRoleOrgRole t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	public void delete(BusinessRoleOrgRole t) {
		// TODO Auto-generated method stub
		dao.delete(t);
	}

	@Override
	public BusinessRoleOrgRole queryById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}

	@Override
	public List<BusinessRoleOrgRole> queryByCondition(BusinessRoleOrgRole t) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(t);
	}

	@Override
	public void deleteByOrgRoleId(String roleId) {
		// TODO Auto-generated method stub
		dao.deleteByCondition("id", roleId);
	}

	@Override
	public List<BusinessRoleOrgRole> queryByCondition(String string, String id) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(string, id);
	}
	
	@Override
	public void deleteByRoleId(String roleId) {
		// TODO Auto-generated method stub
		dao.deleteByRoleId(roleId);
	}
	
}
