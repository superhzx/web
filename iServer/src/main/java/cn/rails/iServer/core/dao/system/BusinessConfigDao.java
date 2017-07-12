/**
 * 
 */
package cn.rails.iServer.core.dao.system;

import java.util.List;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.BusinessConfig;

/**
 * @author dengjm
 * @date 2017年4月19日
 * @description 
 */
public interface BusinessConfigDao extends IBaseDao<BusinessConfig>{
	BusinessConfig queryByCode(String workflowCode);
}
