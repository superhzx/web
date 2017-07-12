/**
 * 
 */
package cn.rails.iServer.core.dao.system.daoImpl;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.FeeClassDao;
import cn.rails.iServer.core.entity.FeeClass;

/**
 * @author dengjm
 * @date 2017年6月5日
 * @description 
 */
@Repository
public class FeeClassDaoImpl extends HibernateBaseDao<FeeClass> implements FeeClassDao{
	@Override
	public Class<FeeClass> getEntityClass() {
		return FeeClass.class;
	}
	
	/**
	 * 删除下级费用
	 * @author dengjm
	 */
	@Override
	public void deleteByCondition(String key, Object value) { 
		String hql = "delete from "+getEntityClass().getSimpleName()+" where 1=1 and "+key+"=?";
		getSession().createQuery(hql).setParameter(0, value).executeUpdate();
	}
}
