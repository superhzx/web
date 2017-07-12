package cn.rails.iServer.core.service.system;

import java.util.List;

import cn.rails.iServer.base.IBaseService;
import cn.rails.iServer.core.entity.BusinessOrgRole;

/**
 * 
 * @author hzx
 * @date 2017年4月10日
 * @description 审核信息
 */
public interface OrgRoleService extends IBaseService<BusinessOrgRole>{
	public void update(BusinessOrgRole t) ;

	/**
	 * @param string
	 * @param orgRoleId
	 * @return
	 */
	public List<BusinessOrgRole> queryByCondition(String string,
			String orgRoleId);
}
