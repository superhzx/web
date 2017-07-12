/**
 * 
 */
package cn.rails.iServer.core.dao.system.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.rails.iServer.base.HibernateBaseDao;
import cn.rails.iServer.core.dao.system.BusinessConfigDao;
import cn.rails.iServer.core.entity.BusinessConfig;
import cn.rails.iServer.core.entity.BusinessRole;
import cn.rails.iServer.core.entity.User;

/**
 * @author dengjm
 * @date 2017年4月19日
 * @description 
 */
@Repository
public class BusinessConfigDaoImpl extends HibernateBaseDao<BusinessConfig> implements BusinessConfigDao{

	@Override
	public Class<BusinessConfig> getEntityClass() {
		return BusinessConfig.class;
	}

	@Override
	public BusinessConfig queryByCode(String workflowCode) {
		String hql="from BusinessConfig where workflowCode =?";
		BusinessConfig businessConfig = (BusinessConfig) getSession().createQuery(hql).setParameter(0,
				workflowCode).uniqueResult();
		return businessConfig;
	}



}
