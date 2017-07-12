package cn.rails.iServer.core.dao.system;
import java.util.List;

import cn.rails.iServer.base.IBaseDao;
import cn.rails.iServer.core.entity.UserRole;
/**
 * <p>Title      : 中国铁道科学研究院[]</p>
 * <p>Description: [人员角色管理]</p>
 * <p>Copyright  : Copyright (c) 2017</p>
 * <p>Company    : 铁科院电子所</p>
 * <p>Department : 信息中心</p>
 * @author       : sunyh
 * @version      : 1.0
 * @date         ：2017-02-16
 */
public interface UserRoleDao extends IBaseDao<UserRole>{
	/**
	 * 保存或更新
	 */
	public void saveOrUpdate(UserRole t);
	public void updateByUserId(String userId,String departmentId);
	public void deleteByUserId(String userId);
	//根据人员ID获取角色信息
	public List<UserRole> getUserRoleByUserId(String userId);
}
