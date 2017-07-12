package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.OrgRoleUserDao;
import cn.rails.iServer.core.dao.system.RoleOrgRoleDao;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.BusinessRoleOrgRole;
/**
 * @author       : hzx
 * @date         ：2017-04-11
 */
@Repository
public class RoleOrgRoleDaoImpl extends HibernateBaseDao<BusinessRoleOrgRole> implements RoleOrgRoleDao {

	@Override
	public void deleteByOrgRoleId(String roleId) {
		String hql ="delete from BusinessRoleOrgRole where businessOrgRoleId =?";
		getSession().createQuery(hql).setParameter(0,roleId).executeUpdate();
	}

	@Override
	public Class<BusinessRoleOrgRole> getEntityClass() {
		// TODO Auto-generated method stub
		return BusinessRoleOrgRole.class;
	}
	
	@Override
	public void deleteByRoleId(String roleId) {
		// TODO Auto-generated method stub
		String hql ="delete from BusinessRoleOrgRole where businessRoleId =?";
		getSession().createQuery(hql).setParameter(0,roleId).executeUpdate();
	}
	
} 