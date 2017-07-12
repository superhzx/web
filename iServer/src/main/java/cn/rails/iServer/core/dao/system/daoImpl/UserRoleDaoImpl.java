package cn.rails.iServer.core.dao.system.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.UserRoleDao;
import cn.rails.iServer.core.entity.UserRole;
/**
 * <p>Title      : 中国铁道科学研究院[]</p>
 * <p>Description: [人员角色管理]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-16
 */
@Repository
public class UserRoleDaoImpl extends HibernateBaseDao<UserRole> implements UserRoleDao {
	@Override
	public Class<UserRole> getEntityClass() {
		return UserRole.class;
	}
	//删除人员角色表中的所有userID下的角色
	@Override
	public void deleteByUserId(String userId) {
		String hql ="delete from UserRole where userId =?";
		getSession().createQuery(hql).setParameter(0,userId).executeUpdate();
	}
	
	@Override
	public void updateByUserId(String userId, String roleId) {
		String sql="update user_role ur set ur.role_ID='"+roleId+"' where ud.USER_ID='"+userId+"'";
		getSession().createSQLQuery(sql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRole> getUserRoleByUserId(String userId) {
		String hql = " from UserRole a where a.userId=?";
		List<UserRole> list  = getSession().createQuery(hql).setParameter(0, userId).list();
		return list;
	}

}
