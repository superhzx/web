package cn.rails.iServer.core.dao.system;


import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.BusinessRoleOrgRole;
/**
 * @author       : hzx
 * @date         ：2017-04-10
 */
public interface RoleOrgRoleDao extends IBaseDao<BusinessRoleOrgRole>{

	void deleteByOrgRoleId(String roleId);

	void deleteByRoleId(String roleId);
}
