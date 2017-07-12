/**
 * 
 */
package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.BusinessConfigDao;
import cn.rails.iServer.core.entity.BusinessConfig;
import cn.rails.iServer.core.entity.BusinessSystem;
import cn.rails.iServer.core.service.system.BusinessConfigService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author dengjm
 * @date 2017年4月19日
 * @description 
 */
@Service
@Transactional
public class BusinessConfigServiceImpl implements BusinessConfigService{
	@Autowired
	private BusinessConfigDao  businessConfigDao;
	
	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#listByPage(int, int, cn.rails.ims.utils.page.Paramter)
	 */
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		// TODO Auto-generated method stub
		return businessConfigDao.listByPage(pageNo, pageSize, par);
	}


	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#save(java.lang.Object)
	 */
	@Override
	public void save(BusinessConfig t) {
		// TODO Auto-generated method stub
		businessConfigDao.save(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#delete(java.lang.Object)
	 */
	@Override
	public void delete(BusinessConfig t) {
		// TODO Auto-generated method stub
		businessConfigDao.delete(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#queryById(java.io.Serializable)
	 */
	@Override
	public BusinessConfig queryById(Serializable id) {
		// TODO Auto-generated method stub
		return businessConfigDao.queryById(id);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#queryByCondition(java.lang.Object)
	 */
	@Override
	public List<BusinessConfig> queryByCondition(BusinessConfig t) {
		// TODO Auto-generated method stub
		return businessConfigDao.queryByCondition(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#update(cn.rails.ims.core.entity.BusinessSystem)
	 */
	@Override
	public void update(BusinessConfig t) {
		// TODO Auto-generated method stub
		businessConfigDao.update(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#updateBusinessSystem(cn.rails.ims.core.entity.BusinessSystem)
	 */
	@Override
	public void updateBusinessConfig(BusinessConfig t) {
		// TODO Auto-generated method stub
		businessConfigDao.update(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#deleteByCondition(java.lang.String, java.lang.Object)
	 */
	@Override
	public void deleteByCondition(String key, Object value) {
		// TODO Auto-generated method stub
		businessConfigDao.deleteByCondition(key, value);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#queryByCondition(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<BusinessConfig> queryByCondition(String key, Object value) {
		// TODO Auto-generated method stub
		return businessConfigDao.queryByCondition(key, value);
	}


	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#deleteBusinessSystemById(java.lang.String)
	 */
	@Override
	public void deleteBusinessConfigById(String id) {
		// TODO Auto-generated method stub
		businessConfigDao.deleteByCondition("id", id);
	}


	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#list()
	 */
	@Override
	public List<BusinessConfig> list() {
		// TODO Auto-generated method stub
		return businessConfigDao.list();
	}


	@Override
	public BusinessConfig queryByCode(String workflowCode) {
		// TODO Auto-generated method stub
		return businessConfigDao.queryByCode(workflowCode);
	}
}
