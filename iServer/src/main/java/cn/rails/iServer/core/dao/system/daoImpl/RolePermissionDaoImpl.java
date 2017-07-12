package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.RolePermissionDao;
import cn.rails.iServer.core.entity.RolePermission;

/**
 * @author wl
 * @date 2017年3月23日
 * @description 角色权限
 */
@Repository
public class RolePermissionDaoImpl extends HibernateBaseDao<RolePermission> implements RolePermissionDao{
	@Override
	public Class<RolePermission> getEntityClass() {
		return RolePermission.class;
	}
	/**
	 * 按照条件删除角色菜单信息
	 * @author sunyh
	 */
	@Override
	public void deleteByCondition(String key, Object value) { 
		String hql = "delete from "+getEntityClass().getSimpleName()+" where 1=1 and "+key+"=?";
		getSession().createQuery(hql).setParameter(0, value).executeUpdate();
	}
}
