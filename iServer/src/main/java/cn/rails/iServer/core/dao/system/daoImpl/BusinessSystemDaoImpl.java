/**
 * 
 */
package cn.rails.iServer.core.dao.system.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.BusinessSystemDao;
import cn.rails.iServer.core.entity.BusinessSystem;
import cn.rails.iServer.core.entity.User;

/**
 * @author dengjm
 * @date 2017年4月10日
 * @description 
 */
@Repository
public class BusinessSystemDaoImpl extends HibernateBaseDao<BusinessSystem> implements BusinessSystemDao{

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.dao.system.BusinessSystemDao#checkLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public int checkLogin(String name, String pwd) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.HibernateBaseDao#getEntityClass()
	 */
	@Override
	public Class<BusinessSystem> getEntityClass() {
		// TODO Auto-generated method stub
		return BusinessSystem.class;
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.dao.system.BusinessSystemDao#deleteBusinessSystemById(java.lang.String)
	 */
	@Override
	public void deleteBusinessSystemById(String id) {
		// TODO Auto-generated method stub
		String hql ="delete from BusinessSystem where Id =?";
		getSession().createQuery(hql).setParameter(0,id).executeUpdate();
	}
	
	//下拉框
	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessSystem> getSelectData(String key, String value){
		// 获取session
		String hql = "select new Users(a.gid,nvl(a.userName,' ')) "
				+ "from Users a ";
		List<BusinessSystem> pro =  (List<BusinessSystem>) getSession().createQuery(hql).list();
		return pro;
	}
}
