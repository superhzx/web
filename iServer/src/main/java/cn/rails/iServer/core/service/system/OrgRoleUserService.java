package cn.rails.iServer.core.service.system;



import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.BusinessOrgRoleUser;

/**
 * 
 * @author hzx
 * @date 2017年4月11日
 * @description  组织角色和用户中间表
 */
public interface OrgRoleUserService extends IBaseService<BusinessOrgRoleUser>{
	
	public void deleteByOrgRoleId(String roleId);
}
