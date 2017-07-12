package cn.rails.iServer.core.dao.system.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.BusinessRoleDao;
import cn.rails.iServer.core.dao.system.CommentDao;
import cn.rails.iServer.core.entity.BusinessRole;
import cn.rails.iServer.core.entity.Comment;
/**
 * @author       : hzx
 * @date         ：2017-04-07
 */
@Repository
public class BusinessRoleDaoImpl extends HibernateBaseDao<BusinessRole> implements BusinessRoleDao {
	@Override
	public Class<BusinessRole> getEntityClass() {
		return BusinessRole.class;
	}

	//下拉框
	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessRole> getSelectData(String aa){
		// 获取session
		String hql = "from BusinessRole a where a.systemId = ?";
		List<BusinessRole> pro=(List<BusinessRole>)getSession().createQuery(hql).setParameter(0,aa).list();
		System.out.println(pro.size());
		return pro;
	}
	

	//下拉框
	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessRole> getSelectData(String key, String value){
		// 获取session
		String hql = "select new BusinessRole(a.id,nvl(a.businessRoleName,' ')) "
				+ "from BusinessRole a ";
		List<BusinessRole> pro=(List<BusinessRole>)getSession().createQuery(hql).list();
		return pro;
	}

	@Override
	public void deleteBySystemId(String systemId) {
		String hql ="delete from BusinessRole where systemId =?";
		getSession().createQuery(hql).setParameter(0,systemId).executeUpdate();
	}
	
} 