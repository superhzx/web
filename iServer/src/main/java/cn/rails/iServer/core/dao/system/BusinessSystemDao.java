/**
 * 
 */
package cn.rails.iServer.core.dao.system;

import java.util.List;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.BusinessSystem;

/**
 * @author dengjm
 * @date 2017年4月10日
 * @description 
 */
public interface BusinessSystemDao extends IBaseDao<BusinessSystem>{
	public int checkLogin(String name,String pwd);
	public void deleteBusinessSystemById(String id);
	
	/**
	 * 获取下拉列表
	 * @param gid
	 * @return
	 */
	public List<BusinessSystem> getSelectData(String key, String value);
}
