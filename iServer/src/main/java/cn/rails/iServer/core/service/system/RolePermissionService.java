package cn.rails.iServer.core.service.system;

import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.RolePermission;
/**
 * @author wl
 * @date 2017年3月23日
 * @description 角色权限
 */
public interface RolePermissionService extends IBaseService<RolePermission>{
	public List<RolePermission> queryByCondition(String key, Object value);
	public void deleteByCondition(String key,Object value);
}
