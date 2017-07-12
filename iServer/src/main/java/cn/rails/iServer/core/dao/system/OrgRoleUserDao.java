package cn.rails.iServer.core.dao.system;


import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
/**
 * @author       : hzx
 * @date         ：2017-04-10
 */
public interface OrgRoleUserDao extends IBaseDao<BusinessOrgRoleUser>{

	void deleteByOrgRoleId(String roleId);

	
}
