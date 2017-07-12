/**
 * 
 */
package cn.rails.iServer.core.service.system;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.FeeClass;

/**
 * @author dengjm
 * @date 2017年6月5日
 * @description 
 */
public interface FeeClassService extends IBaseService<FeeClass>{
	public void deleteByCondition(String key,Object value);
}
