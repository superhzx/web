/**
 * 
 */
package cn.rails.iServer.core.service.system;

import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.BusinessSystem;

/**
 * @author dengjm
 * @date 2017年4月10日
 * @description 
 */
public interface BusinessSystemService extends IBaseService<BusinessSystem>{
	public void update (BusinessSystem t);
	public void updateBusinessSystem(BusinessSystem t);
	public void deleteByCondition(String key,Object value);
	public List<BusinessSystem> queryByCondition(String key,Object value);
	
}
