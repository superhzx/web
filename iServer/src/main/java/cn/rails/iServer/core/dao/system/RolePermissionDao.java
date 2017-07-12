package cn.rails.iServer.core.dao.system;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.RolePermission;

/**
 * @author wl
 * @date 2017年3月23日
 * @description 角色权限
 */
public interface RolePermissionDao extends IBaseDao<RolePermission> {
	/**
	 * 按条件删除角色菜单信息
	 */
	public void deleteByCondition(String key,Object value);
}
