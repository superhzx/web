package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.BusinessRoleDao;
import cn.rails.iServer.core.dao.system.CommentDao;
import cn.rails.iServer.core.dao.system.OrgRoleDao;
import cn.rails.iServer.core.entity.BusinessOrgRole;
import cn.rails.iServer.core.entity.BusinessRole;
import cn.rails.iServer.core.entity.Comment;
/**
 * @author       : hzx
 * @date         ：2017-04-07
 */
@Repository
public class OrgRoleDaoImpl extends HibernateBaseDao<BusinessOrgRole> implements OrgRoleDao {
	@Override
	public Class<BusinessOrgRole> getEntityClass() {
		return BusinessOrgRole.class;
	}

	
} 