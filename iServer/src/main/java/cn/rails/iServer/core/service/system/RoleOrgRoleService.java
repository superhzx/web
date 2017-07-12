package cn.rails.iServer.core.service.system;



import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;
import cn.rails.iServer.core.entity.BusinessRoleOrgRole;

/**
 * 
 * @author hzx
 * @date 2017年4月11日
 * @description  组织角色和用户中间表
 */
public interface RoleOrgRoleService extends IBaseService<BusinessRoleOrgRole>{
	
	public void deleteByOrgRoleId(String roleId);

	public List<BusinessRoleOrgRole> queryByCondition(String string, String id);
	
	public void deleteByRoleId(String roleId);
}
