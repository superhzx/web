package cn.rails.iServer.core.dao.system;


import java.util.List;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.BusinessRole;
import cn.rails.iServer.core.entity.Comment;
/**
 * @author       : hzx
 * @date         ：2017-04-10
 */
public interface BusinessRoleDao extends IBaseDao<BusinessRole>{
	public List<BusinessRole> getSelectData(String aa);
	public List<BusinessRole> getSelectData(String key, String value);
	public void deleteBySystemId(String systemId);

}
