/**
 * 
 */
package cn.rails.iServer.core.service.system.serviceImpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rails.iServer.core.dao.system.FeeClassDao;
import cn.rails.iServer.core.entity.FeeClass;
import cn.rails.iServer.core.service.system.FeeClassService;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author dengjm
 * @date 2017年6月5日
 * @description 
 */
@Service
@Transactional
public class FeeClassServiceImpl implements FeeClassService{

	@Autowired
	private FeeClassDao  feeClassDao;
	
	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#listByPage(int, int, cn.rails.ims.utils.page.Paramter)
	 */
	@Override
	public PageTion listByPage(int pageNo, int pageSize, Paramter par) {
		// TODO Auto-generated method stub
		return feeClassDao.listByPage(pageNo, pageSize, par);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#list()
	 */
	@Override
	public List<FeeClass> list() {
		// TODO Auto-generated method stub
		return feeClassDao.list();
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#save(java.lang.Object)
	 */
	@Override
	public void save(FeeClass t) {
		// TODO Auto-generated method stub
		feeClassDao.save(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#update(java.lang.Object)
	 */
	@Override
	public void update(FeeClass t) {
		// TODO Auto-generated method stub
		feeClassDao.update(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#delete(java.lang.Object)
	 */
	@Override
	public void delete(FeeClass t) {
		// TODO Auto-generated method stub
		feeClassDao.delete(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#queryById(java.io.Serializable)
	 */
	@Override
	public FeeClass queryById(Serializable id) {
		// TODO Auto-generated method stub
		return feeClassDao.queryById(id);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.base.IBaseService#queryByCondition(java.lang.Object)
	 */
	@Override
	public List<FeeClass> queryByCondition(FeeClass t) {
		// TODO Auto-generated method stub
		return feeClassDao.queryByCondition(t);
	}

	/* (non-Javadoc)
	 * @see cn.rails.ims.core.service.system.FeeClassService#deleteByCondition(java.lang.String, java.lang.Object)
	 */
	@Override
	public void deleteByCondition(String key, Object value) {
		// TODO Auto-generated method stub
		feeClassDao.deleteByCondition(key, value);
	}

}
