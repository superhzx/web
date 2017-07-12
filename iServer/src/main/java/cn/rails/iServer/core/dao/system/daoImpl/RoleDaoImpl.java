package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.RoleDao;
import cn.rails.iServer.core.entity.Role;

/**
 * 
 * @author hzx
 * @date 2017年3月27日
 * @description 角色DAO层实现类
 */

@Repository
public class RoleDaoImpl extends HibernateBaseDao<Role> implements RoleDao {

	@Override
	public Class<Role> getEntityClass() {
		return Role.class;
	}
}
