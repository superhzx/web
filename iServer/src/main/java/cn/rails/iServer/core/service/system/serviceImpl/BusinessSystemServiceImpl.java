/**
 * 
 */
package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.BusinessSystemDao;
import cn.rails.iServer.core.entity.BusinessSystem;
import cn.rails.iServer.core.service.system.BusinessSystemService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author dengjm
 * @date 2017年4月10日
 * @description 
 */
@Service
@Transactional
public class BusinessSystemServiceImpl implements BusinessSystemService{
	@Autowired
	private BusinessSystemDao dao;

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#listByPage(int, int, cn.rails.ims.utils.page.Paramter)
	 */
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		// TODO Auto-generated method stub
		return dao.listByPage(pageNo, pageSize, par);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#list()
	 */
	@Override
	public List<BusinessSystem> list() {
		// TODO Auto-generated method stub
		return dao.list();
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#save(java.lang.Object)
	 */
	@Override
	public void save(BusinessSystem t) {
		// TODO Auto-generated method stub
		dao.save(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#delete(java.lang.Object)
	 */
	@Override
	public void delete(BusinessSystem t) {
		// TODO Auto-generated method stub
		dao.delete(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#queryById(java.io.Serializable)
	 */
	@Override
	public BusinessSystem queryById(Serializable id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#queryByCondition(java.lang.Object)
	 */
	@Override
	public List<BusinessSystem> queryByCondition(BusinessSystem t) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#update(cn.rails.ims.core.entity.BusinessSystem)
	 */
	@Override
	public void update(BusinessSystem t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#updateBusinessSystem(cn.rails.ims.core.entity.BusinessSystem)
	 */
	@Override
	public void updateBusinessSystem(BusinessSystem t) {
		// TODO Auto-generated method stub
		dao.update(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#deleteByCondition(java.lang.String, java.lang.Object)
	 */
	@Override
	public void deleteByCondition(String key, Object value) {
		// TODO Auto-generated method stub
		dao.deleteByCondition(key, value);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.BusinessSystenService#queryByCondition(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<BusinessSystem> queryByCondition(String key, Object value) {
		// TODO Auto-generated method stub
		return dao.queryByCondition(key, value);
	}
	

}
