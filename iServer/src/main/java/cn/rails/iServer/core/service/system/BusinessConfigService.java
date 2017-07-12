/**
 * 
 */
package cn.rails.iServer.core.service.system;

import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.BusinessConfig;
import cn.rails.iServer.core.entity.BusinessSystem;
import cn.rails.iServer.utils.page.PageTion;
import cn.rails.iServer.utils.page.Paramter;

/**
 * @author dengjm
 * @date 2017年4月19日
 * @description 
 */
public interface BusinessConfigService extends IBaseService<BusinessConfig>{

	/**
	 * @param pageNo
	 * @param pageSize
	 * @param par
	 * @return
	 */
	PageTion listByPage(int pageNo, int pageSize, Paramter par);

	/**
	 * @param id
	 */
	void deleteBusinessConfigById(String id);

	/**
	 * @param key
	 * @param value
	 */
	void deleteByCondition(String key, Object value);

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	List<BusinessConfig> queryByCondition(String key, Object value);

	/**
	 * @param t
	 */
	void updateBusinessConfig(BusinessConfig t);

	BusinessConfig queryByCode(String workflowCode);

}
