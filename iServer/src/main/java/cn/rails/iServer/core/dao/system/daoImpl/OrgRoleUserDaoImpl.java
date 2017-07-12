package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.OrgRoleUserDao;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
/**
 * @author       : hzx
 * @date         ：2017-04-11
 */
@Repository
public class OrgRoleUserDaoImpl extends HibernateBaseDao<BusinessOrgRoleUser> implements OrgRoleUserDao {
	@Override
	public Class<BusinessOrgRoleUser> getEntityClass() {
		return BusinessOrgRoleUser.class;
	}

	@Override
	public void deleteByOrgRoleId(String roleId) {
		String hql ="delete from BusinessOrgRoleUser where orgRoleId =?";
		getSession().createQuery(hql).setParameter(0,roleId).executeUpdate();
	}

	
	
} 