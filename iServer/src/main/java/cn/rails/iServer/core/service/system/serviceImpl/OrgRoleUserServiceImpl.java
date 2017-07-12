package cn.rails.iServer.core.service.system.serviceImpl;
import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.OrgRoleUserDao;
import cn.rails.iServer.core.dao.system.UserRoleDao;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.UserRole;
import cn.rails.iServer.core.service.system.OrgRoleUserService;
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
public class OrgRoleUserServiceImpl implements OrgRoleUserService{
	@Autowired
	private OrgRoleUserDao dao ;

	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		// TODO Auto-generated method stub
		return dao.listByPage(pageNo, pageSize, par);
	}

	@Override
	public List<BusinessOrgRoleUser> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	@Override
	public void save(BusinessOrgRoleUser t) {
		// TODO Auto-generated method stub
		dao.save(t);
	}

	@Override
	public void update(BusinessOrgRoleUser t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	@Override
	public void delete(BusinessOrgRoleUser t) {
		// TODO Auto-generated method stub
		dao.delete(t);
	}

	@Override
	public BusinessOrgRoleUser queryById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}

	@Override
	public List<BusinessOrgRoleUser> queryByCondition(BusinessOrgRoleUser t) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(t);
	}

	@Override
	public void deleteByOrgRoleId(String roleId) {
		
		dao.deleteByOrgRoleId(roleId);
	}

}
