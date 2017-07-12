/**
 * 
 */
package cn.rails.iServer.core.dao.system;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.FeeClass;

/**
 * @author dengjm
 * @date 2017年6月5日
 * @description 
 */
public interface FeeClassDao extends IBaseDao<FeeClass>{
	public void deleteByCondition(String key,Object value);
}
